package com.my.library_base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewConfiguration
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.my.library_base.base.inf.IBaseActivity
import com.my.library_base.base.inf.IBaseView
import com.my.library_res.R
import org.apache.log4j.Logger
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 * @version 1.0
 */
abstract class BaseActivity<V : ViewDataBinding?, VM : BaseViewModel<*>?> : AppCompatActivity(), IBaseActivity, IBaseView {
    protected var logger: Logger = Logger.getLogger(this.javaClass)
    private var binding: V? = null
    protected var viewModel: VM? = null
    private var viewModelId = 0

    /**
     * 当前Activity的弱引用，防止内存泄露
     */
    private var context: WeakReference<Activity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("BaseActivity-->onCreate()")

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState)
        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        //注册RxBus
        viewModel!!.registerRxBus()

        //将当前Activity压入栈
        context = WeakReference(this)

        //显示VoerFlowMenu
        displayOverflowMenu(getContext())
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClass: Class<*>
            val type = javaClass.genericSuperclass
            modelClass = if (type is ParameterizedType) ({
                type.actualTypeArguments[1] as Class<*>
            }) as Class<*> else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        }
        //关联ViewModel
        binding!!.setVariable(viewModelId, viewModel)
        binding!!.lifecycleOwner = this
    }

    override fun onRestart() {
        super.onRestart()
        logger.info("BaseActivity-->onRestart()")
    }

    override fun onStart() {
        super.onStart()
        logger.info("BaseActivity-->onStart()")
    }

    override fun onResume() {
        super.onResume()
        logger.info("BaseActivity-->onResume()")
        resume()
    }

    override fun onPause() {
        super.onPause()
        logger.info("BaseActivity-->onPause()")
    }

    override fun onStop() {
        super.onStop()
        logger.info("BaseActivity-->onStop()")
    }

    override fun onDestroy() {
        logger.info("BaseActivity-->onDestroy()")
        viewModel?.removeRxBus()
        binding?.unbind()
        destroy()
        super.onDestroy()
    }

    /**
     * 显示Actionbar菜单图标
     */
    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.javaClass.simpleName == "MenuBuilder") {
                try {
                    val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    m.isAccessible = true
                    m.invoke(menu, true) //显示
                } catch (e: Exception) {
                    logger.info("onMenuOpened-->" + e.message)
                }
            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    /**
     * 显示OverFlowMenu按钮
     *
     * @param mContext 上下文Context
     */
    private fun displayOverflowMenu(mContext: Context?) {
        try {
            val config = ViewConfiguration.get(mContext)
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if (menuKeyField != null) {
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config, false) //显示
            }
        } catch (e: Exception) {
            Log.e("ActionBar", e.message!!)
        }
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected fun getContext(): Activity? {
        return if (null != context) context!!.get() else null
    }

    /**
     * Actionbar点击返回键关闭事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                //关闭窗体动画显示
                overridePendingTransition(0, R.anim.base_slide_right_out)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //关闭窗体动画显示
        overridePendingTransition(0, R.anim.base_slide_right_out)
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
     protected fun initViewModel(): VM? {
       return null
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel?> createViewModel(activity: AppCompatActivity, cls: Class<*>): T {
        cls as Class<T>
        return ViewModelProvider(activity, AndroidViewModelFactory.getInstance(activity.application)).get(cls)
    }

    override fun initViewObservable() {}
}
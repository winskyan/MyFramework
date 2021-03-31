package com.my.library_base.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.my.library_base.base.BaseViewModel
import com.my.library_base.base.inf.IBaseFragment
import com.my.library_base.base.inf.IBaseView
import org.apache.log4j.Logger
import java.lang.reflect.ParameterizedType

/**
 * Fragment基类
 *
 * @version 1.0
 */
abstract class BaseFragment<V : ViewDataBinding?, VM : BaseViewModel<*>?> : Fragment(), IBaseFragment, IBaseView {
    protected var logger = Logger.getLogger(this.javaClass)
    protected var binding: V? = null
    protected var viewModel: VM? = null
    private var viewModelId = 0
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        logger.info("BaseFragment-->onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("BaseFragment-->onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()

        //页面数据初始化方法
        initData()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        //注册RxBus
        viewModel!!.registerRxBus()
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        viewModelId = initVariableId()
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClass: Class<*>
            val type = javaClass.genericSuperclass
            modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<*>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        }
        binding!!.setVariable(viewModelId, viewModel)
        //binding.setLifecycleOwner(this);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        logger.info("BaseFragment-->onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        logger.info("BaseFragment-->onSaveInstanceState()")
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        logger.info("BaseFragment-->onStart()")
        super.onStart()
    }

    override fun onResume() {
        logger.info("BaseFragment-->onResume()")
        super.onResume()
        resume()
    }

    override fun onPause() {
        logger.info("BaseFragment-->onPause()")
        super.onPause()
    }

    override fun onStop() {
        logger.info("BaseFragment-->onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        logger.info("BaseFragment-->onDestroy()")
        destroy()
        viewModel?.removeRxBus()
        binding?.unbind()
        super.onDestroy()
    }

    override fun onDetach() {
        logger.info("BaseFragment-->onDetach()")
        super.onDetach()
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int

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
    fun initViewModel(): VM? {
        return null
    }

    override fun initData() {}
    override fun initViewObservable() {}

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T : ViewModel?> createViewModel(fragment: Fragment, cls: Class<*>?): T {
        cls as Class<T>
        return ViewModelProvider(fragment, AndroidViewModelFactory(fragment.activity!!.application)).get(cls)
    }
}
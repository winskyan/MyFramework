package com.my.library_base.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.my.library_base.base.inf.IBaseActivity;
import com.my.library_base.base.inf.IBaseView;
import com.my.library_base.model.EventBusMessageEvent;
import com.my.library_res.R;
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;


/**
 * @version 1.0
 */
public abstract class MVVMBaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseActivity, IBaseView {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("BaseActivity-->onCreate()");

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();

        //将当前Activity压入栈
        context = new WeakReference<Activity>(this);

        //显示VoerFlowMenu
        displayOverflowMenu(getContext());

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        BaseInit.initStatusBar(this);
        BaseInit.initTitleBar(this);
    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.info("BaseActivity-->onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.info("BaseActivity-->onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.info("BaseActivity-->onResume()");
        resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.info("BaseActivity-->onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.info("BaseActivity-->onStop()");
    }

    @Override
    protected void onDestroy() {
        logger.info("BaseActivity-->onDestroy()");
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }

        EventBus.getDefault().unregister(this);
        destroy();
        super.onDestroy();
    }

    /**
     * 显示Actionbar菜单图标
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);//显示
                } catch (Exception e) {
                    logger.info("onMenuOpened-->" + e.getMessage());
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 显示OverFlowMenu按钮
     *
     * @param mContext 上下文Context
     */
    public void displayOverflowMenu(Context mContext) {
        try {
            ViewConfiguration config = ViewConfiguration.get(mContext);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);//显示
            }
        } catch (Exception e) {
            Log.e("ActionBar", e.getMessage());
        }
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected Activity getContext() {
        if (null != context)
            return context.get();
        else
            return null;
    }


    /**
     * Actionbar点击返回键关闭事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //关闭窗体动画显示
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //关闭窗体动画显示
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(AppCompatActivity activity, Class<T> cls) {
        return new ViewModelProvider(activity, ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(cls);
    }

    @Override
    public void initViewObservable() {

    }

    // 在主线程展示 Toast 结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageEvent event) {
    }
}

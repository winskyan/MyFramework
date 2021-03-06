package com.my.library_base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.my.library_base.base.inf.IBaseFragment;
import com.my.library_base.base.inf.IBaseView;
import com.my.library_base.model.EventBusMessageEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @version 1.0
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment implements IBaseFragment, IBaseView {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("BaseFragment-->onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initContentView(inflater, container, savedInstanceState), null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(getActivity());

        EventBus.getDefault().register(this);

        //将当前Activity压入栈
        context = new WeakReference<Activity>(getActivity());

        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.info("BaseFragment-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        logger.info("BaseFragment-->onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        logger.info("BaseFragment-->onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        logger.info("BaseFragment-->onResume()");
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        logger.info("BaseFragment-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        logger.info("BaseFragment-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        logger.info("BaseFragment-->onDestroy()");
        EventBus.getDefault().unregister(this);
        destroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        logger.info("BaseFragment-->onDetach()");
        super.onDetach();
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    // 在主线程展示 Toast 结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageEvent event) {
    }
}

package com.my.library_base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.my.library_base.base.inf.IBaseFragment;
import com.my.library_base.base.inf.IBaseView;

import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment基类
 *
 * @version 1.0
 */
@SuppressLint("NewApi")
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBaseFragment, IBaseView {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected V binding;
    protected VM viewModel;
    private int viewModelId;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        logger.info("BaseFragment-->onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("BaseFragment-->onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();

        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
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
        binding.setVariable(viewModelId, viewModel);
        //binding.setLifecycleOwner(this);
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
        destroy();
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
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

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(fragment, new ViewModelProvider.AndroidViewModelFactory(fragment.getActivity().getApplication())).get(cls);
    }
}

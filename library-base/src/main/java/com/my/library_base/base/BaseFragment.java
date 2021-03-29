package com.my.library_base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.log4j.Logger;

/**
 * Fragment基类
 * @author 曾繁添
 * @version 1.0
 *
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment implements IBaseFragment {
	protected Logger logger = Logger.getLogger(this.getClass());
	/**当前Fragment渲染的视图View**/
	private View mContextView = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		logger.info("BaseFragment-->onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logger.info( "BaseFragment-->onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		logger.info("BaseFragment-->onCreateView()");
		
		//渲染视图View(防止切换时重绘View)
        if (null != mContextView) {
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (null != parent) {
                parent.removeView(mContextView);
            }
        } else {
        	mContextView = inflater.inflate(bindLayout(), container);
        	// 控件初始化
            initView(mContextView);
            
        }
        
		//业务处理
		doBusiness(getActivity());
		
		return mContextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		logger.info("BaseFragment-->onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		logger.info( "BaseFragment-->onSaveInstanceState()");
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
	}

	@Override
	public void onPause() {
		logger.info("BaseFragment-->onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		logger.info( "BaseFragment-->onStop()");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		logger.info( "BaseFragment-->onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		logger.info("BaseFragment-->onDetach()");
		super.onDetach();
	}
	
	/**
	 * 获取当前Fragment依附在的Activity
	 * @return
	 */
	protected Activity getActivityContext(){
		return getActivity();
	}
}

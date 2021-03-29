package com.my.library_base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.my.library_res.R;

import org.apache.log4j.Logger;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * android 系统中的四大组件之一Activity基类
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class BaseActivity extends Activity implements IBaseActivity {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("BaseActivity-->onCreate()");

        //设置渲染视图View
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);

        //将当前Activity压入栈
        context = new WeakReference<Activity>(this);

        //初始化控件
        initView(mContextView);

        //业务操作
        doBusiness(this);

        //显示VoerFlowMenu
        displayOverflowMenu(getContext());
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
        super.onDestroy();
        logger.info("BaseActivity-->onDestroy()");

        destroy();
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
}

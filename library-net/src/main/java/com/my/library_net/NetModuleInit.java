package com.my.library_net;

import android.app.Application;
import android.content.Context;

import com.my.library_base.init.IModuleInit;
import com.my.library_base.logs.GLog;


/**
 * Created by yan
 * 基础库自身初始化操作
 */

public class NetModuleInit implements IModuleInit {
    public static Context applicationContext;

    @Override
    public boolean onInit(Application application) {
        GLog.i("Net层初始化 -- onInitAhead");
        initData(application);
        return true;
    }

    @Override
    public boolean onDestroy(Application application) {
        GLog.i("Net层销毁 -- onDestroy");
        return true;
    }

    private void initData(Application application) {
        applicationContext = application.getApplicationContext();
    }

}

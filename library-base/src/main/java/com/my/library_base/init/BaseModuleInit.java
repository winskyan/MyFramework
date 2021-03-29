package com.my.library_base.init;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.XXPermissions;
import com.my.library_base.BuildConfig;
import com.my.library_base.logs.ConfigureLog4j;
import com.my.library_base.logs.GLog;
import com.tencent.mmkv.MMKV;

import static android.os.Looper.getMainLooper;


/**
 * Created by yan on 2020/7/15
 * 基础库自身初始化操作
 */

public class BaseModuleInit implements IModuleInit {
    public static Context applicationContext;

    @Override
    public boolean onInitAhead(Application application) {
        GLog.i("基础层初始化 -- onInitAhead");
        initData(application);
        ConfigureLog4j.initLog4j();
        //开启打印日志
        GLog.init(true);

        if (BuildConfig.LOG_DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化

        MMKV.initialize(application);

        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true);
        return true;
    }

    @Override
    public boolean onInitLow(Application application) {
        GLog.i("基础层初始化 -- onInitLow");
        return true;
    }

    private void initData(Application application) {
        applicationContext = application.getApplicationContext();
    }

}

package com.my.library_base.init;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.XXPermissions;
import com.liulishuo.filedownloader.FileDownloader;
import com.my.library_base.BuildConfig;
import com.my.library_base.config.SysConfig;
import com.my.library_base.constants.Constants;
import com.my.library_base.constants.KeyConstants;
import com.my.library_base.logs.ConfigureLog4j;
import com.my.library_base.logs.GLog;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by yan on 2020/7/15
 * 基础库自身初始化操作
 */

public class BaseModuleInit implements IModuleInit {
    public static Context applicationContext;

    @Override
    public boolean onInit(Application application) {
        GLog.i("基础层初始化 -- onInit");
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

        if (SysConfig.UM_ENABLE) {
            UMConfigure.init(application, KeyConstants.UM_SDK, Constants.DEFAULT_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, "");

            //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
            UMConfigure.setLogEnabled(SysConfig.UM_LOG);
            // 支持在子进程中统计自定义事件
            UMConfigure.setProcessEvent(true);
        }

        FileDownloader.setupOnApplicationOnCreate(application);

        return true;
    }

    @Override
    public boolean onDestroy(Application application) {
        GLog.i("基础层销毁 -- onDestroy");
        return true;
    }

    private void initData(Application application) {
        applicationContext = application.getApplicationContext();
    }

}

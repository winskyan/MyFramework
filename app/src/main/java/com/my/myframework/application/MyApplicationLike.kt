package com.my.myframework.application

import android.annotation.TargetApi
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.multidex.MultiDex
import com.my.library_base.config.SysConfig
import com.my.library_base.init.ModuleLifecycleConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.entry.DefaultApplicationLike

class MyApplicationLike(application: Application?, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent?) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {
    override fun onCreate() {
        super.onCreate()
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        if (SysConfig.BUGLY_CRASH_ENABLE) {
            Bugly.init(application, "ad480f08fa", false)
        }
        ModuleLifecycleConfig.instance.initModule(application)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        if (SysConfig.TINKER_ENABLE) {
            Beta.installTinker(this)
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: ActivityLifecycleCallbacks?) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    override fun onTerminate() {
        super.onTerminate()
        //fix me,该方法不保证执行
        ModuleLifecycleConfig.instance.destroyModule(application)
    }
}
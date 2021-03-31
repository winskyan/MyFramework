package com.my.library_base.debug

import android.app.Application
import com.my.library_base.init.ModuleLifecycleConfig

class DebugApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化组件(靠前)
        ModuleLifecycleConfig.instance.initModule(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ModuleLifecycleConfig.instance.destroyModule(this)
    }
}
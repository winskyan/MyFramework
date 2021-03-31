package com.my.module_settings

import android.app.Application
import android.content.Context
import com.my.library_base.init.IModuleInit
import com.my.library_base.logs.GLog.i

/**
 * Created by yan
 */
class SettingsModuleInit : IModuleInit {
    override fun onInit(application: Application?): Boolean {
        init(application)
        i("Settings业务模块初始化 -- onInitAhead")
        return true
    }

    override fun onDestroy(application: Application?): Boolean {
        i("Settings业务模块销毁 -- onDestroy")
        return true
    }

    private fun init(application: Application?) {
        context = application!!.applicationContext
    }

    companion object {
        var context: Context? = null
    }
}
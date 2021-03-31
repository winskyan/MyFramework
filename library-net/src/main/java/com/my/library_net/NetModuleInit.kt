package com.my.library_net

import android.app.Application
import android.content.Context
import com.my.library_base.init.IModuleInit
import com.my.library_base.logs.GLog

/**
 * Created by yan
 * 基础库自身初始化操作
 */
class NetModuleInit constructor() : IModuleInit {
    override fun onInit(application: Application): Boolean {
        GLog.i("Net层初始化 -- onInitAhead")
        initData(application)
        return true
    }

    override fun onDestroy(application: Application): Boolean {
        GLog.i("Net层销毁 -- onDestroy")
        return true
    }

    private fun initData(application: Application) {
        applicationContext = application.applicationContext
    }

    companion object {
        var applicationContext: Context? = null
    }
}
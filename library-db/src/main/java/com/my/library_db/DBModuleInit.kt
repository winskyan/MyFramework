package com.my.library_db

import android.app.Application
import android.content.Context
import com.my.library_base.init.IModuleInit
import com.my.library_base.logs.GLog
import com.my.library_db.db.DatabaseInit

/**
 * Created by yan
 * 基础库自身初始化操作
 */
class DBModuleInit : IModuleInit {
    override fun onInit(application: Application): Boolean {
        GLog.i("DB层初始化 -- onInitAhead")
        initData(application)
        DatabaseInit.instance!!.init(application)
        return true
    }

    override fun onDestroy(application: Application): Boolean {
        GLog.i("DB层销毁 -- onDestroy")
        return true
    }

    private fun initData(application: Application) {
        applicationContext = application.applicationContext
    }

    companion object {
        var applicationContext: Context? = null
    }
}
package com.my.library_base.init

import android.app.Application

interface IModuleInit {
    //模块初始化
    fun onInit(application: Application?): Boolean

    //模块销毁
    fun onDestroy(application: Application?): Boolean
}
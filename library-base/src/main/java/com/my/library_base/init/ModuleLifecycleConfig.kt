package com.my.library_base.init

import android.app.Application
import com.my.library_base.logs.GLog

/**
 * Created by yan on 2020/7/15
 */
class ModuleLifecycleConfig private constructor() {
    //内部类，在装载该内部类时才会去创建单例对象
    private object SingletonHolder {
        var instance = ModuleLifecycleConfig()
    }

    //初始化组件-靠前
    fun initModule(application: Application?) {
        for (moduleInitName in ModuleLifecycleReflexs.initModuleNames) {
            try {
                val clazz = Class.forName(moduleInitName)
                val init = clazz.newInstance() as IModuleInit
                //调用初始化方法
                if (!init.onInit(application)) {
                    GLog.e("基础层初始化失败 -- onInitAhead")
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //销毁组件
    fun destroyModule(application: Application?) {
        for (moduleInitName in ModuleLifecycleReflexs.initModuleNames) {
            try {
                val clazz = Class.forName(moduleInitName)
                val init = clazz.newInstance() as IModuleInit
                //调用初始化方法
                if (!init.onDestroy(application)) {
                    GLog.e("基础层销毁失败 -- onInitLow")
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val instance: ModuleLifecycleConfig
            get() = SingletonHolder.instance
    }
}
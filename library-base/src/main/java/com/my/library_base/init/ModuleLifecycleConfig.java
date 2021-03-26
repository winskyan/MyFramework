package com.my.library_base.init;

import android.app.Application;

import androidx.annotation.Nullable;

import com.my.library_base.logs.GLog;

/**
 * Created by yan on 2020/7/15
 */

public class ModuleLifecycleConfig {
    //内部类，在装载该内部类时才会去创建单例对象
    private static class SingletonHolder {
        public static ModuleLifecycleConfig instance = new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance() {
        return SingletonHolder.instance;
    }

    private ModuleLifecycleConfig() {
    }

    //初始化组件-靠前
    public void initModuleAhead(@Nullable Application application) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                //调用初始化方法
                if (!init.onInitAhead(application)) {
                    GLog.e("基础层初始化失败 -- onInitAhead");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //初始化组件-靠后
    public void initModuleLow(@Nullable Application application) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                //调用初始化方法
                if (!init.onInitLow(application)) {
                    GLog.e("基础层初始化失败 -- onInitLow");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

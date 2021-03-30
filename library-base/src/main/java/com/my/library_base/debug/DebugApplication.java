package com.my.library_base.debug;

import android.app.Application;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.my.library_base.init.ModuleLifecycleConfig;

public class DebugApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModule(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ModuleLifecycleConfig.getInstance().destroyModule(this);
    }
}

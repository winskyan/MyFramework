package com.my.library_db;

import android.app.Application;
import android.content.Context;

import com.my.library_base.init.IModuleInit;
import com.my.library_base.logs.GLog;
import com.my.library_db.db.DatabaseInit;


/**
 * Created by yan
 * 基础库自身初始化操作
 */

public class DBModuleInit implements IModuleInit {
    public static Context applicationContext;

    @Override
    public boolean onInitAhead(Application application) {
        GLog.i("DB层初始化 -- onInitAhead");
        initData(application);
        DatabaseInit.getInstance().init(application);
        return true;
    }

    @Override
    public boolean onInitLow(Application application) {
        GLog.i("DB层初始化 -- onInitLow");
        return true;
    }

    private void initData(Application application) {
        applicationContext = application.getApplicationContext();
    }

}

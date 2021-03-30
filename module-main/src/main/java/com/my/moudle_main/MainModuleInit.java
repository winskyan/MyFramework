package com.my.moudle_main;


import android.app.Application;
import android.content.Context;

import com.my.library_base.init.IModuleInit;
import com.my.library_base.logs.GLog;

/**
 * Created by yan
 */

public class MainModuleInit implements IModuleInit {
    public static Context context;

    @Override
    public boolean onInit(Application application) {
        init(application);

        GLog.i("Main业务模块初始化 -- onInitAhead");
        return true;
    }


    @Override
    public boolean onDestroy(Application application) {
        GLog.i("Main业务模块销毁 -- onDestroy");
        return true;
    }

    private void init(Application application) {
        context = application.getApplicationContext();
    }
}

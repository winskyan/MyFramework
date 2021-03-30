package com.my.library_base.init;

import android.app.Application;


public interface IModuleInit {

    //模块初始化
    boolean onInit(Application application);

    //模块销毁
    boolean onDestroy(Application application);
}

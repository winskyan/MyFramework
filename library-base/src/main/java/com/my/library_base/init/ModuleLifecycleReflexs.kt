package com.my.library_base.init

/**
 * Created by yan
 */
object ModuleLifecycleReflexs {
    private const val BASE_INIT = "com.my.library_base.init.BaseModuleInit"
    private const val DB_INIT = "com.my.library_db.DBModuleInit"
    private const val NET_INIT = "com.my.library_net.NetModuleInit"

    //主业务模块
    private const val MAIN_INIT = "com.my.moudle_main.MainModuleInit"

    //settings模块
    private const val SETTINGS_INIT = "com.my.module_settings.SettingsModuleInit"
    var initModuleNames = arrayOf(BASE_INIT, DB_INIT, NET_INIT, MAIN_INIT, SETTINGS_INIT)
}
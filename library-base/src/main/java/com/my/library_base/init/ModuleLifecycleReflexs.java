package com.my.library_base.init;

/**
 * Created by yan
 */
public class ModuleLifecycleReflexs {
    private static final String BASE_INIT = "com.my.library_base.init.BaseModuleInit";
    private static final String DB_INIT = "com.my.library_db.DBModuleInit";
    private static final String NET_INIT = "com.my.library_net.NetModuleInit";

    //主业务模块
    private static final String MAIN_INIT = "com.my.moudle_main.MainModuleInit";
    //settings模块
    private static final String SETTINGS_INIT = "com.my.module_settings.SettingsModuleInit";


    public static String[] initModuleNames = {BASE_INIT, DB_INIT, NET_INIT, MAIN_INIT, SETTINGS_INIT};
}

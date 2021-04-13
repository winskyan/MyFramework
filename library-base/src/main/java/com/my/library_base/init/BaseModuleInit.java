package com.my.library_base.init;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.XXPermissions;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.InputInfo;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.v3.Notification;
import com.liulishuo.filedownloader.FileDownloader;
import com.my.library_base.BuildConfig;
import com.my.library_base.config.SysConfig;
import com.my.library_base.constants.Constants;
import com.my.library_base.constants.KeyConstants;
import com.my.library_base.logs.ConfigureLog4j;
import com.my.library_base.logs.GLog;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by yan on 2020/7/15
 * 基础库自身初始化操作
 */

public class BaseModuleInit implements IModuleInit {
    public static Context applicationContext;

    @Override
    public boolean onInit(Application application) {
        GLog.i("基础层初始化 -- onInit");
        initData(application);
        ConfigureLog4j.initLog4j();
        //开启打印日志
        GLog.init(true);

        if (BuildConfig.LOG_DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化

        MMKV.initialize(application);

        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true);

        if (SysConfig.UM_ENABLE) {
            UMConfigure.init(application, KeyConstants.UM_SDK, Constants.DEFAULT_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, "");

            //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
            UMConfigure.setLogEnabled(SysConfig.UM_LOG);
            // 支持在子进程中统计自定义事件
            UMConfigure.setProcessEvent(true);
        }

        FileDownloader.setupOnApplicationOnCreate(application);


        DialogSettings.isUseBlur = true;                   //是否开启模糊效果，默认关闭
        DialogSettings.modalDialog = true;                 //是否开启模态窗口模式，一次显示多个对话框将以队列形式一个一个显示，默认关闭
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;          //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
        DialogSettings.theme = DialogSettings.THEME.LIGHT;          //全局对话框明暗风格，提供两种可选主题，LIGHT, DARK
        DialogSettings.tipTheme = DialogSettings.THEME.LIGHT;       //全局提示框明暗风格，提供两种可选主题，LIGHT, DARK
        TextInfo textInfo = new TextInfo();
        DialogSettings.titleTextInfo = textInfo;              //全局对话框标题文字样式
        DialogSettings.menuTitleInfo = textInfo;              //全局菜单标题文字样式
        DialogSettings.menuTextInfo = textInfo;               //全局菜单列表文字样式
        DialogSettings.contentTextInfo = textInfo;            //全局正文文字样式
        DialogSettings.buttonTextInfo = textInfo;             //全局默认按钮文字样式
        DialogSettings.buttonPositiveTextInfo = textInfo;     //全局焦点按钮文字样式（一般指确定按钮）
        DialogSettings.inputInfo = new InputInfo();                 //全局输入框文本样式
        DialogSettings.backgroundColor = 0;            //全局对话框背景颜色，值0时不生效
        DialogSettings.cancelable = false;                  //全局对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
        DialogSettings.cancelableTipDialog = false;         //全局提示框及等待框（WaitDialog、TipDialog）默认是否可以关闭
        DialogSettings.DEBUGMODE = BuildConfig.LOG_DEBUG;                   //是否允许打印日志
        DialogSettings.blurAlpha = 125;                       //开启模糊后的透明度（0~255）
        //DialogSettings.systemDialogStyle = (styleResId);        //自定义系统对话框style，注意设置此功能会导致原对话框风格和动画失效
        //DialogSettings.dialogLifeCycleListener = (DialogLifeCycleListener);  //全局Dialog生命周期监听器
        //DialogSettings.defaultCancelButtonText = (String);      //设置 BottomMenu 和 ShareDialog 默认“取消”按钮的文字
        //DialogSettings.tipBackgroundResId = (drawableResId);    //设置 TipDialog 和 WaitDialog 的背景资源
        DialogSettings.tipTextInfo = textInfo;               //设置 TipDialog 和 WaitDialog 文字样式
        DialogSettings.autoShowInputKeyboard = true;       //设置 InputDialog 是否自动弹出输入法
        //DialogSettings.okButtonDrawable = (drawable);           //设置确定按钮背景资源
        //DialogSettings.cancelButtonDrawable = (drawable);       //设置取消按钮背景资源
        //DialogSettings.otherButtonDrawable = (drawable);        //设置其他按钮背景资源
        Notification.mode = Notification.Mode.FLOATING_WINDOW;  //通知实现方式。可选 TOAST 使用自定义吐司实现以及 FLOATING_WINDOW 悬浮窗实现方式
        //检查 Renderscript 兼容性，若设备不支持，DialogSettings.isUseBlur 会自动关闭；
        boolean renderscriptSupport = DialogSettings.checkRenderscriptSupport(application);
        DialogSettings.init();                           //初始化清空 BaseDialog 队列

        return true;
    }

    @Override
    public boolean onDestroy(Application application) {
        GLog.i("基础层销毁 -- onDestroy");
        return true;
    }

    private void initData(Application application) {
        applicationContext = application.getApplicationContext();
    }

}

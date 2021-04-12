package com.my.library_base.utils;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class Utils {
    public static void aRouteGoto(String path, Map<String, String> args) {
        Postcard postcard = ARouter.getInstance().build(path);
        if (null != args) {
            for (Map.Entry<String, String> entry : args.entrySet()) {
                postcard.withString(entry.getKey(), entry.getValue());
            }
        }
        postcard.navigation();
    }

    public static String getStringByResId(@NotNull Context context, @NotNull int resId) {
        return context.getResources().getString(resId);
    }

}

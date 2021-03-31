package com.my.library_base.utils

import com.alibaba.android.arouter.launcher.ARouter

object Utils {
    @kotlin.jvm.JvmStatic
    fun aRouteGoto(path: String?, args: Map<String?, String?>?) {
        val postcard = ARouter.getInstance().build(path)
        if (null != args) {
            for ((key, value) in args) {
                postcard.withString(key, value)
            }
        }
        postcard.navigation()
    }
}
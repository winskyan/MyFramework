package com.my.library_base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object NetWorkUtils {
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取NetworkInfo对象
            var network: Network? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                network = manager.activeNetwork
            }
            //判断NetworkInfo对象是否为空
            if (network != null) {
                val networkCapabilities = manager.getNetworkCapabilities(network)
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    }
                } else {
                    return false
                }
            }
        }
        return false
    }

    //判断WIFI网络是否可用
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取NetworkInfo对象
            var network: Network? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                network = manager.activeNetwork
            }
            //判断NetworkInfo对象是否为空
            if (network != null) {
                val networkCapabilities = manager.getNetworkCapabilities(network)
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    }
                } else {
                    return false
                }
            }
        }
        return false
    }

    /*
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取NetworkInfo对象
            var network: Network? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                network = manager.activeNetwork
            }
            //判断NetworkInfo对象是否为空
            if (network != null) {
                val networkCapabilities = manager.getNetworkCapabilities(network)
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    }
                } else {
                    return false
                }
            }
        }
        return false
    }
}
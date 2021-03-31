package com.my.library_base.base

import android.app.Service
import org.apache.log4j.Logger

/**
 * android 系统中的四大组件之一Service基类
 * @version 1.0
 */
abstract class BaseService : Service() {
    /**日志输出标志 */
    protected var logger = Logger.getLogger(this.javaClass)
}
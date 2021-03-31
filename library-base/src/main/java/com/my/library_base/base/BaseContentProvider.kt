package com.my.library_base.base

import android.content.ContentProvider
import org.apache.log4j.Logger

/**
 * android 系统中的四大组件之一ContentProvider基类
 * @version 1.0
 */
abstract class BaseContentProvider : ContentProvider() {
    protected var logger = Logger.getLogger(this.javaClass)
}
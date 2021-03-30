package com.my.library_base.base;

import android.content.ContentProvider;

import org.apache.log4j.Logger;

/**
 * android 系统中的四大组件之一ContentProvider基类
 * @version 1.0
 *
 */
public abstract class BaseContentProvider extends ContentProvider {
	protected Logger logger = Logger.getLogger(this.getClass());
}

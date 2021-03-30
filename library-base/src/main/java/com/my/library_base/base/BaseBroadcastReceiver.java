package com.my.library_base.base;

import android.content.BroadcastReceiver;

import org.apache.log4j.Logger;

/**
 *  android 系统中的四大组件之一BroadcastReceiver基类<生命周期只有十秒左右，耗时操作需开service来做><br>
 * @version 1.0
 *
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
	protected Logger logger = Logger.getLogger(this.getClass());

}

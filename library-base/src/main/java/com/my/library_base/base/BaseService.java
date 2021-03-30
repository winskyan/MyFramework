package com.my.library_base.base;

import android.app.Service;

import org.apache.log4j.Logger;

/**
 *  android 系统中的四大组件之一Service基类
 * @version 1.0
 *
 */
public abstract class BaseService extends Service {

	/**日志输出标志**/
	protected Logger logger = Logger.getLogger(this.getClass());
}

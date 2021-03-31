package com.my.library_base.base.inf

/**
 * Activity接口
 *
 * @version 1.0
 */
interface IBaseActivity {
    /**
     * 暂停恢复刷新相关操作（onResume方法中调用）
     */
    fun resume()

    /**
     * 销毁、释放资源相关操作（onDestroy方法中调用）
     */
    fun destroy()
}
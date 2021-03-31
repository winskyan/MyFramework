package com.my.library_base.base.inf

import android.view.View

/**
 * Fragment接口
 * @version 1.0
 */
interface IBaseFragment {
    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
    fun createView(): View?

    /**
     * 暂停恢复刷新相关操作（onResume方法中调用）
     */
    fun resume()

    /**
     * 销毁、释放资源相关操作（onDestroy方法中调用）
     */
    fun destroy()
}
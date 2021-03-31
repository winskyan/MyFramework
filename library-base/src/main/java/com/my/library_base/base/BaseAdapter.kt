package com.my.library_base.base

import android.app.Activity
import android.widget.BaseAdapter
import org.apache.log4j.Logger
import java.util.*

abstract class BaseAdapter @JvmOverloads constructor(
        /** Context上下文  */
        private val mContext: Activity? = null, mPerPageSize: Int = 10) : BaseAdapter() {
    protected var logger = Logger.getLogger(this.javaClass)

    /** 数据存储集合  */
    private val mDataList: MutableList<Any> = ArrayList()

    /** 每一页显示条数  */
    private var mPerPageSize = 10
    override fun getCount(): Int {
        return mDataList.size
    }

    override fun getItem(position: Int): Any? {
        return if (position < mDataList.size) mDataList[position] else null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 获取当前页
     * @return 当前页
     */
    val pageNo: Int
        get() = count / mPerPageSize + 1

    /**
     * 添加数据
     * @param object 数据项
     */
    fun addItem(`object`: Any): Boolean {
        return mDataList.add(`object`)
    }

    /**
     * 在指定索引位置添加数据
     * @param location 索引
     * @param object 数据
     */
    fun addItem(location: Int, `object`: Any) {
        mDataList.add(location, `object`)
    }

    /**
     * 集合方式添加数据
     * @param collection 集合
     */
    fun addItem(collection: Collection<Any>?): Boolean {
        return mDataList.addAll(collection!!)
    }

    /**
     * 在指定索引位置添加数据集合
     * @param location 索引
     * @param collection 数据集合
     */
    fun addItem(location: Int, collection: Collection<Any>?): Boolean {
        return mDataList.addAll(location, collection!!)
    }

    /**
     * 移除指定对象数据
     * @param object 移除对象
     * @return 是否移除成功
     */
    fun removeItem(`object`: Any): Boolean {
        return mDataList.remove(`object`)
    }

    /**
     * 移除指定索引位置对象
     * @param location 删除对象索引位置
     * @return 被删除的对象
     */
    fun removeItem(location: Int): Any {
        return mDataList.removeAt(location)
    }

    /**
     * 移除指定集合对象
     * @param collection 待移除的集合
     * @return 是否移除成功
     */
    fun removeAll(collection: Collection<Any>?): Boolean {
        return mDataList.removeAll(collection!!)
    }

    /**
     * 清空数据
     */
    fun clear() {
        mDataList.clear()
    }

    /**
     * 获取Activity方法
     * @return Activity的子类
     */
    val activity: Activity?
        get() {
            if (null == mContext) return null
            return if (mContext is BaseActivity<*, *>) mContext else null
        }

    init {
        this.mPerPageSize = mPerPageSize
    }
}
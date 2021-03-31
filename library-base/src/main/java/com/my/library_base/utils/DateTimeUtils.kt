package com.my.library_base.utils

import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    const val DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     */
    const val DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"

    /**
     * 日期格式：yyyy-MM-dd
     */
    const val DF_YYYY_MM_DD = "yyyy-MM-dd"

    /**
     * 日期格式：HH:mm:ss
     */
    const val DF_HH_MM_SS = "HH:mm:ss"

    /**
     * 日期格式：HH:mm
     */
    const val DF_HH_MM = "HH:mm"
    private const val minute = (60 * 1000 // 1分钟
            ).toLong()
    private const val hour = 60 * minute // 1小时
    private const val day = 24 * hour // 1天
    private const val month = 31 * day // 月
    private const val year = 12 * month // 年

    /**
     * Log输出标识
     */
    private val TAG = DateTimeUtils::class.java.simpleName

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    fun formatFriendly(date: Date?): String? {
        if (date == null) {
            return null
        }
        val diff = Date().time - date.time
        var r: Long = 0
        if (diff > year) {
            r = diff / year
            return r.toString() + "年前"
        }
        if (diff > month) {
            r = diff / month
            return r.toString() + "个月前"
        }
        if (diff > day) {
            r = diff / day
            return r.toString() + "天前"
        }
        if (diff > hour) {
            r = diff / hour
            return r.toString() + "个小时前"
        }
        if (diff > minute) {
            r = diff / minute
            return r.toString() + "分钟前"
        }
        return "刚刚"
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    fun formatDateTime(dateL: Long): String {
        val sdf = SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS)
        val date = Date(dateL)
        return sdf.format(date)
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    fun formatDateTime(dateL: Long, formater: String?): String {
        val sdf = SimpleDateFormat(formater)
        return sdf.format(Date(dateL))
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param date 日期
     * @return
     */
    fun formatDateTime(date: Date?, formater: String?): String {
        val sdf = SimpleDateFormat(formater)
        return sdf.format(date)
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    fun parseDate(strDate: String?): Date? {
        val dateFormat: DateFormat = SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS)
        var returnDate: Date? = null
        try {
            returnDate = dateFormat.parse(strDate)
        } catch (e: ParseException) {
            Log.v(TAG, "parseDate failed !")
        }
        return returnDate
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    fun gainCurrentDate(): Date {
        return Date()
    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1 比较时间1
     * @param target2 比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    fun compareDate(target1: Date?, target2: Date?): Boolean {
        var flag = false
        try {
            val target1DateTime = formatDateTime(target1,
                    DF_YYYY_MM_DD_HH_MM_SS)
            val target2DateTime = formatDateTime(target2,
                    DF_YYYY_MM_DD_HH_MM_SS)
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true
            }
        } catch (e1: Exception) {
            println("比较失败，原因：" + e1.message)
        }
        return flag
    }

    /**
     * 对日期进行增加操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    fun addDateTime(target: Date?, hour: Double): Date? {
        return if (null == target || hour < 0) {
            target
        } else Date(target.time + (hour * 60 * 60 * 1000).toLong())
    }

    /**
     * 对日期进行相减操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    fun subDateTime(target: Date?, hour: Double): Date? {
        return if (null == target || hour < 0) {
            target
        } else Date(target.time - (hour * 60 * 60 * 1000).toLong())
    }
}
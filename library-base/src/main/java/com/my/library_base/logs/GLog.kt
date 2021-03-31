package com.my.library_base.logs

import android.text.TextUtils
import android.util.Log
import com.my.library_base.constants.Constants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object GLog {
    private var IS_SHOW_LOG = true
    private var logger: Logger? = null
    private const val mIsInitConfigure = false
    private const val DEFAULT_MESSAGE = "execute"
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private const val JSON_INDENT = 4
    private const val D = 0x1
    private const val I = 0x2
    private const val W = 0x3
    private const val E = 0x4
    private const val JSON = 0x5
    fun init(isShowLog: Boolean) {
        IS_SHOW_LOG = isShowLog
    }

    fun d() {
        printLog(D, DEFAULT_MESSAGE)
    }

    fun d(msg: Any?) {
        printLog(D, msg)
    }

    fun i() {
        printLog(I, DEFAULT_MESSAGE)
    }

    @kotlin.jvm.JvmStatic
    fun i(msg: Any?) {
        printLog(I, msg)
    }

    fun w() {
        printLog(W, DEFAULT_MESSAGE)
    }

    fun w(msg: Any?) {
        printLog(W, msg)
    }

    fun e() {
        printLog(E, DEFAULT_MESSAGE)
    }

    fun e(msg: Any?) {
        printLog(E, msg)
    }

    fun json(jsonFormat: String?) {
        printLog(JSON, jsonFormat)
    }

    private fun printLog(type: Int, objectMsg: Any?) {
        val msg: String
        if (!IS_SHOW_LOG) {
            return
        }
        val stackTrace = Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        logger = LoggerFactory.getLogger(stackTrace[index].className)
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[(").append(className).append(":").append(lineNumber).append(")#").append(methodName).append("]")
        msg = objectMsg?.toString() ?: "Log with null Object"
        if (msg != null && type != JSON) {
            stringBuilder.append(msg)
        }
        val logStr = stringBuilder.toString()
        when (type) {
            D, I, W, E -> printMsg(type, logStr)
            JSON -> {
                if (TextUtils.isEmpty(msg)) {
                    printMsg(D, "Empty or Null json content")
                    return
                }
                var message: String? = null
                try {
                    if (msg.startsWith("{")) {
                        val jsonObject = JSONObject(msg)
                        message = jsonObject.toString(JSON_INDENT)
                    } else if (msg.startsWith("[")) {
                        val jsonArray = JSONArray(msg)
                        message = jsonArray.toString(JSON_INDENT)
                    }
                } catch (e: JSONException) {
                    printMsg(E, """
     ${e.cause!!.message}
     $msg
     """.trimIndent())
                    return
                }
                printLine(true)
                message = logStr + LINE_SEPARATOR + message
                val lines = message.split(LINE_SEPARATOR.toRegex()).toTypedArray()
                val jsonContent = StringBuilder()
                for (line in lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR)
                }
                //Log.i(tag, jsonContent.toString());
                if (jsonContent.toString().length > 3200) {
                    printMsg(W, "jsonContent.length = " + jsonContent.toString().length)
                    val chunkCount = jsonContent.toString().length / 3200
                    var i = 0
                    while (i <= chunkCount) {
                        val max = 3200 * (i + 1)
                        if (max >= jsonContent.toString().length) {
                            printMsg(W, jsonContent.toString().substring(3200 * i))
                        } else {
                            printMsg(W, jsonContent.toString().substring(3200 * i, max))
                        }
                        i++
                    }
                } else {
                    printMsg(W, jsonContent.toString())
                }
                printLine(false)
            }
        }
    }

    private fun printLine(isTop: Boolean) {
        if (isTop) {
            printMsg(W, "╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            printMsg(W, "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
    }

    private fun printMsg(level: Int, msg: String) {
        if (mIsInitConfigure) {
            if (null == logger) {
                return
            }
            when (level) {
                D -> logger!!.debug(msg)
                I -> logger!!.info(msg)
                W -> logger!!.warn(msg)
                E -> logger!!.error(msg)
                else -> {
                }
            }
        } else {
            when (level) {
                D -> Log.d(Constants.TAG, msg)
                I -> Log.i(Constants.TAG, msg)
                W -> Log.w(Constants.TAG, msg)
                E -> Log.e(Constants.TAG, msg)
                else -> {
                }
            }
        }
    }
}
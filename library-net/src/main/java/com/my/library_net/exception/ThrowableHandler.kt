package com.my.library_net.exception

import com.google.gson.JsonParseException
import org.apache.http.HttpException
import org.apache.http.ParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

object ThrowableHandler {
    fun handleThrowable(throwable: Throwable): HttpThrowable {
        return if (throwable is HttpException) {
            HttpThrowable(HttpThrowable.Companion.HTTP_ERROR, "网络(协议)异常", throwable)
        } else if (throwable is JsonParseException || throwable is JSONException || throwable is ParseException) {
            HttpThrowable(HttpThrowable.Companion.PARSE_ERROR, "数据解析异常", throwable)
        } else if (throwable is UnknownHostException) {
            HttpThrowable(HttpThrowable.Companion.NO_NET_ERROR, "网络连接失败，请稍后重试", throwable)
        } else if (throwable is SocketTimeoutException) {
            HttpThrowable(HttpThrowable.Companion.TIME_OUT_ERROR, "连接超时", throwable)
        } else if (throwable is ConnectException) {
            HttpThrowable(HttpThrowable.Companion.CONNECT_ERROR, "连接异常", throwable)
        } else if (throwable is SSLHandshakeException) {
            HttpThrowable(HttpThrowable.Companion.SSL_ERROR, "证书验证失败", throwable)
        } else {
            HttpThrowable(HttpThrowable.Companion.UNKNOWN, throwable.message, throwable)
        }
    }
}
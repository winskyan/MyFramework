package com.my.library_net.exception

class HttpThrowable constructor(var errorType: Int, override var message: String?, var throwable: Throwable) : Exception(throwable) {
    override fun toString(): String {
        return ("HttpThrowable{" +
                "errorType=" + errorType +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                '}')
    }

    companion object {
        /**
         * 未知错误
         */
        val UNKNOWN: Int = 1000

        /**
         * 解析错误
         */
        val PARSE_ERROR: Int = 1001

        /**
         * 连接错误
         */
        val CONNECT_ERROR: Int = 1002

        /**
         * DNS解析失败（无网络）
         */
        val NO_NET_ERROR: Int = 1003

        /**
         * 连接超时错误
         */
        val TIME_OUT_ERROR: Int = 1004

        /**
         * 网络（协议）错误
         */
        val HTTP_ERROR: Int = 1005

        /**
         * 证书错误
         */
        val SSL_ERROR: Int = 1006
    }
}
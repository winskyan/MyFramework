package com.my.library_net.response

class Response<T> constructor() {
    var body: T? = null
        private set
    var message: String? = null
    var error: String? = null
    var status: String? = null

    override fun toString(): String {
        return ("ResponseBean{" +
                "body=" + body +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                '}')
    }
}
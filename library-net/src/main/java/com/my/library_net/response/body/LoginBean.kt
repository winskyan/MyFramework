package com.my.library_net.response.body

class LoginBean constructor() {
    var needVerifyCode: String? = null
    override fun toString(): String {
        return ("LoginBean{" +
                "needVerifyCode='" + needVerifyCode + '\'' +
                '}')
    }
}
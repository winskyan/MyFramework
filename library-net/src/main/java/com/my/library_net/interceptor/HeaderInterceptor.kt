package com.my.library_net.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder = originalRequest.newBuilder()
        //设置具体的header内容
        builder.header("timestamp", System.currentTimeMillis().toString() + "")
        val requestBuilder: Request.Builder = builder.method(originalRequest.method(), originalRequest.body())
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
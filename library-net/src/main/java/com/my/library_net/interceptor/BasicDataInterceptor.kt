package com.my.library_net.interceptor

import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder

class BasicDataInterceptor constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest: Request = chain.request()
        val httpUrl: HttpUrl = originRequest.url()
        val requestUrl: StringBuilder = StringBuilder()
        requestUrl.append(httpUrl.url().toString() + "?")
        try {
            // GET方法
            if ((METHOD_GET == originRequest.method())) {
                requestUrl.append(httpUrl.toString())
            } else if ((METHOD_POST == originRequest.method())) {
                val requestBody: RequestBody? = originRequest.body()
                if (requestBody != null) {
                    val msg: String = "-url--" + originRequest.url()
                    if (msg.contains("uploadFile")) {
                        requestUrl.append("--上传文件内容--")
                    } else {
                        requestUrl.append(getParam(requestBody))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chain.proceed(originRequest)
    }

    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private fun getParam(requestBody: RequestBody): String {
        val buffer: Buffer = Buffer()
        var logparm: String
        try {
            requestBody.writeTo(buffer)
            logparm = buffer.readUtf8()
            logparm = URLDecoder.decode(logparm, "utf-8")
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        return logparm
    }

    companion object {
        private const val METHOD_GET: String = "GET"
        private const val METHOD_POST: String = "POST"
    }
}
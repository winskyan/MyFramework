package com.my.library_net.init

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.my.library_base.config.SysConfig
import com.my.library_net.NetModuleInit
import com.my.library_net.constants.Constants
import com.my.library_net.converter.GsonConverterFactory
import com.my.library_net.interceptor.BasicDataInterceptor
import com.my.library_net.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    private val cookieJar: ClearableCookieJar
    fun getRetrofit(host: String?): Retrofit {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS) //设置连接超时时间
                .readTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS) //设置读取超时时间
                .writeTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS) //设置写的超时时间
                .cookieJar(cookieJar)
                .retryOnConnectionFailure(true) //错误重连
        //调试模式打印Log日志
        val logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (SysConfig.CAPTURE_PACK_DISABLE) {
            builder.proxy(Proxy.NO_PROXY)
        }
        if (SysConfig.HTTP_LOG) {
            //显示日志
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(logInterceptor)
        builder.addInterceptor(BasicDataInterceptor())
        //服务器端未配置缓存配置
        //builder.addInterceptor(new CacheInterceptor());
        builder.addInterceptor(HeaderInterceptor())
        val client: OkHttpClient = builder.build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        private var manager: RetrofitManager? = null
        val instance: RetrofitManager?
            get() {
                if (manager == null) {
                    synchronized(RetrofitManager::class.java) {
                        if (manager == null) {
                            manager = RetrofitManager()
                        }
                    }
                }
                return manager
            }
    }

    init {
        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(NetModuleInit.Companion.applicationContext))
    }
}
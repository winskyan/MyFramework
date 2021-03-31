package com.my.library_net.net

import android.annotation.SuppressLint
import com.my.library_net.callback.ResponseCallback
import com.my.library_net.constants.Constants
import com.my.library_net.init.RetrofitManager
import com.my.library_net.response.body.LoginBean
import com.my.library_net.service.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginManage private constructor() {
    private val loginService: LoginService = RetrofitManager.instance!!.getRetrofit(Constants.HOST).create(LoginService::class.java)

    @SuppressLint("CheckResult")
    fun login(callback: ResponseCallback<LoginBean?>?, userName: String?) {
        loginService.getDisplayVerifyCode(userName)
                .subscribeOn(Schedulers.io()) //指定网络请求在io后台线程中进行
                .observeOn(Schedulers.io()) //指定doOnNext的操作在io后台线程进行
                .doOnNext(//doOnNext里的方法执行完毕，observer里的onNext、onError等方法才会执行。
                        Consumer { })
                .observeOn(AndroidSchedulers.mainThread()) //指定observer回调在UI主线程中进行
                .subscribe({ response -> callback?.accept(response) }) { throwable -> callback!!.onError(throwable) } //发起请求，请求的结果先回调到doOnNext进行处理，再回调到observer中
    }

    companion object {
        private var loginManage: LoginManage? = null
        val instance: LoginManage?
            get() {
                if (loginManage == null) {
                    synchronized(LoginManage::class.java) {
                        if (loginManage == null) {
                            loginManage = LoginManage()
                        }
                    }
                }
                return loginManage
            }
    }

}
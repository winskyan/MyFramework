package com.my.library_net.net;

import com.my.library_net.callback.ResponseCallback;
import com.my.library_net.constants.Constants;
import com.my.library_net.init.RetrofitManager;
import com.my.library_net.response.Response;
import com.my.library_net.response.body.LoginBean;
import com.my.library_net.service.LoginService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginManage {
    private static LoginManage loginManage;
    private LoginService loginService;

    private LoginManage() {
        loginService = RetrofitManager.getInstance().getRetrofit(Constants.HOST).create(LoginService.class);
    }

    public static LoginManage getInstance() {
        if (loginManage == null) {
            synchronized (LoginManage.class) {
                if (loginManage == null) {
                    loginManage = new LoginManage();
                }
            }
        }
        return loginManage;
    }

    public void login(final ResponseCallback<LoginBean> callback,String userName) {
        loginService.getDisplayVerifyCode(userName)
                .subscribeOn(Schedulers.io()) //指定网络请求在io后台线程中进行
                .observeOn(Schedulers.io()) //指定doOnNext的操作在io后台线程进行
                .doOnNext(new Consumer<Response<LoginBean>>() {
                    //doOnNext里的方法执行完毕，observer里的onNext、onError等方法才会执行。
                    @Override
                    public void accept(Response<LoginBean> response) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //指定observer回调在UI主线程中进行
                .subscribe(new Consumer<Response<LoginBean>>() {
                    @Override
                    public void accept(Response<LoginBean> response) throws Exception {
                        if (null != callback) {
                            callback.accept(response);
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError(throwable);
                    }
                }); //发起请求，请求的结果先回调到doOnNext进行处理，再回调到observer中

    }
}

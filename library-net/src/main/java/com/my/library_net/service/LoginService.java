package com.my.library_net.service;

import com.my.library_net.response.Response;
import com.my.library_net.response.body.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET("crm/loginBefore.do")
    Observable<Response<LoginBean>> getDisplayVerifyCode(@Query("userName") String userName);
}

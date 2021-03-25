package com.my.library_net.response.body;

public class LoginBean {
    private String needVerifyCode;

    public String getNeedVerifyCode() {
        return needVerifyCode;
    }

    public void setNeedVerifyCode(String needVerifyCode) {
        this.needVerifyCode = needVerifyCode;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "needVerifyCode='" + needVerifyCode + '\'' +
                '}';
    }
}

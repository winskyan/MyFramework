package com.my.library_net.callback;


public interface ResponseBodyCallback {
    void accept(String response);
    void onError(Throwable throwable);
}

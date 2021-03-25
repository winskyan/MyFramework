package com.my.library_net.callback;

import com.my.library_net.response.Response;

public interface ResponseCallback<T> {
    void accept(Response<T> response);
    void onError(Throwable throwable);
}

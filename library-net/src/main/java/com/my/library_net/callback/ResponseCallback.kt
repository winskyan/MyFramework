package com.my.library_net.callback

import com.my.library_net.response.Response

open interface ResponseCallback<T> {
    fun accept(response: Response<T>?)
    fun onError(throwable: Throwable?)
}
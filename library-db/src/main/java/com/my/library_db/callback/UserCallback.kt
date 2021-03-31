package com.my.library_db.callback

import com.my.library_db.model.User

interface UserCallback {
    fun onLoadUsers(users: List<User?>?)
    fun onAdded()
    fun onDeleted()
    fun onUpdated()
    fun onError(err: String?)
}
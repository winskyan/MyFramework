package com.my.myframework.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.my.library_base.base.BaseViewModel
import com.my.library_db.callback.UserCallback
import com.my.library_db.db.UserDatabase
import com.my.myframework.model.User

class MainViewModel(application: Application) : BaseViewModel<User?>(application) {
    var userData: MutableLiveData<User?>
        get() {
            return userData
        }

    fun initUser() {
        val userCallback: UserCallback = object : UserCallback {
            override fun onLoadUsers(users: List<com.my.library_db.model.User?>?) {
                if (users != null) {
                    val user = User()
                    user.firstName = users[0]?.firstName
                    user.lastName = users[0]?.lastName
                    userData.postValue(user)
                }
            }

            override fun onAdded() {}
            override fun onDeleted() {}
            override fun onUpdated() {}
            override fun onError(err: String?) {
                userData.postValue(null)
            }
        }
        UserDatabase.userDatabase?.getAllUser(userCallback)
    }

    init {
        userData = MutableLiveData()
    }
}
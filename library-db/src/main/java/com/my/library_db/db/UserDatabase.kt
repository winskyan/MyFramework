package com.my.library_db.db

import com.my.library_db.callback.UserCallback
import com.my.library_db.dao.UserDao
import com.my.library_db.model.User
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserDatabase private constructor() {
    private val userDao: UserDao
    fun getAllUser(userCallback: UserCallback?) {
        userDao
                .all
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { entities -> userCallback?.onLoadUsers(entities) }
    }

    fun addUser(userCallback: UserCallback, vararg users: User?) {
        Completable
                .fromAction { userDao.insertAll(*users) } //在主线程进行反馈
                .observeOn(AndroidSchedulers.mainThread()) //在io线程进行数据库操作
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onComplete() {
                        userCallback.onAdded()
                    }

                    override fun onError(e: Throwable) {
                        userCallback.onError(e.message)
                    }
                })
    }

    companion object {
        var userDatabase: UserDatabase? = null
            get() {
                if (null == field) {
                    field = UserDatabase()
                }
                return field
            }
            private set
    }

    init {
        userDao = DatabaseInit.instance!!.database!!.userDao()
    }
}
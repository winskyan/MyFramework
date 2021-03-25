package com.my.library_db.db;

import com.my.library_db.callback.UserCallback;
import com.my.library_db.dao.UserDao;
import com.my.library_db.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserDatabase {
    private static UserDatabase userDatabase;
    private UserDao userDao;

    private UserDatabase() {
        userDao = DatabaseInit.getInstance().getDatabase().userDao();
    }

    public static UserDatabase getUserDatabase() {
        if (null == userDatabase) {
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    public void getAllUser(final UserCallback userCallback) {
        userDao
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> entities) {
                        if (null != userCallback) {
                            userCallback.onLoadUsers(entities);
                        }
                    }
                });
    }

    public void addUser(final UserCallback userCallback, final User... users) {
        Completable
                .fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        userDao.insertAll(users);
                    }
                })
                //在主线程进行反馈
                .observeOn(AndroidSchedulers.mainThread())
                //在io线程进行数据库操作
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        userCallback.onAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        userCallback.onError(e.getMessage());
                    }
                });
    }
}


package com.my.myframework.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.my.library_base.base.BaseViewModel;
import com.my.library_db.callback.UserCallback;
import com.my.library_db.db.UserDatabase;
import com.my.myframework.model.User;

import java.util.List;


public class MainViewModel extends BaseViewModel<User> {
    private MutableLiveData<User> userData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        userData = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public void initUser() {
        UserCallback userCallback = new UserCallback() {
            @Override
            public void onLoadUsers(List<com.my.library_db.model.User> users) {
                if (users != null) {
                    User user = new User();
                    user.setFirstName(users.get(0).getFirstName());
                    user.setLastName(users.get(0).getLastName());
                    userData.postValue(user);
                }
            }

            @Override
            public void onAdded() {
            }

            @Override
            public void onDeleted() {

            }

            @Override
            public void onUpdated() {

            }

            @Override
            public void onError(String err) {
                userData.postValue(null);
            }
        };
        UserDatabase.getUserDatabase().getAllUser(userCallback);
    }

}

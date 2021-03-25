package com.my.library_db.callback;

import com.my.library_db.model.User;

import java.util.List;

public interface UserCallback {
    void onLoadUsers(List<User> users);

    void onAdded();

    void onDeleted();

    void onUpdated();

    void onError(String err);
}

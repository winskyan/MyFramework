package com.my.library_db.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.my.library_db.dao.UserDao;
import com.my.library_db.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}


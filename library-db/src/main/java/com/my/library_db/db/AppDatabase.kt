package com.my.library_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.library_db.dao.UserDao
import com.my.library_db.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
package com.my.library_db.db

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.my.library_db.constants.Constants

class DatabaseInit private constructor() {
    var database: AppDatabase? = null
        private set
        get() {
            return database
        }

    fun init(application: Application?) {
        database = Room.databaseBuilder(application!!,
                AppDatabase::class.java, Constants.DB_NAME).addMigrations(MIGRATION_1_2).build()
    }

    companion object {
        private var databaseInit: DatabaseInit? = null
        val instance: DatabaseInit?
            get() {
                if (null == databaseInit) {
                    databaseInit = DatabaseInit()
                }
                return databaseInit
            }
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 既然什么都没有更改，那就来个空的实现。
                //database.execSQL("ALTER TABLE users ADD COLUMN last_update INTEGER");
            }
        }
    }
}
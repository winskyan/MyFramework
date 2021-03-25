package com.my.library_db.db;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.my.library_db.constants.Constants;

public class DatabaseInit {
    private static DatabaseInit databaseInit;

    private AppDatabase db;

    private DatabaseInit() {

    }

    public void init(Application application) {
        db = Room.databaseBuilder(application,
                AppDatabase.class, Constants.DB_NAME).addMigrations(MIGRATION_1_2).build();
    }

    public static DatabaseInit getInstance() {
        if (null == databaseInit) {
            databaseInit = new DatabaseInit();
        }
        return databaseInit;
    }

    public AppDatabase getDatabase() {
        return db;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 既然什么都没有更改，那就来个空的实现。
            //database.execSQL("ALTER TABLE users ADD COLUMN last_update INTEGER");
        }
    };
}

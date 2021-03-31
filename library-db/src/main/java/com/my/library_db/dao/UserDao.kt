package com.my.library_db.dao

import androidx.room.*
import com.my.library_db.model.User
import io.reactivex.Flowable

@Dao
interface UserDao {
    @get:Query("SELECT * FROM user")
    val all: Flowable<List<User?>?>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): Flowable<List<User?>?>?

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String?, last: String?): Flowable<User?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User?)

    @Delete
    fun delete(user: User?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg users: User?)
}
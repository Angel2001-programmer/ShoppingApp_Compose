package com.angel.shoppingapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun add(user: User)

    @Query("SELECT * FROM user_table WHERE userName LIKE :name AND password LIKE:password")
    fun getAll(name: String, password: String){

    }
}
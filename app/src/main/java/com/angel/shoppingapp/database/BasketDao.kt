package com.angel.shoppingapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(basket: Basket)

    @Query("SELECT * FROM basket_table")
    fun getAll(): List<Basket>

    @Delete
    fun delete(basket: Basket)

    @Query("DELETE FROM basket_table")
    suspend fun deleteAll()
}
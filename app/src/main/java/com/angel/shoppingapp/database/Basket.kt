package com.angel.shoppingapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table")
data class Basket(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val image: String?,
    val title: String?,
    val price: Double?
)

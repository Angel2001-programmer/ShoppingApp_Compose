package com.angel.shoppingapp.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user_table")
class User(
    @PrimaryKey
    val id: UUID,
    var userName: String?,
    var password: String?
)
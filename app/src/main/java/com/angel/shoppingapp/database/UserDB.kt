package com.angel.shoppingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

    @Database(
        entities = arrayOf(User::class),
        exportSchema = true,
        version = 2,
    )

    abstract class UserDB : RoomDatabase() {
        abstract fun userDao(): UserDao

        companion object {
            private var INSTANCE : UserDB? = null

            fun getDatabase(context: Context) : UserDB{
                val tempInstance = INSTANCE
                if (tempInstance != null){
                    return tempInstance
                }
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
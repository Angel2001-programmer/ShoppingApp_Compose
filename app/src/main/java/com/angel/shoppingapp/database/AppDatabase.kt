package com.angel.shoppingapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(Basket::class),
    exportSchema = true,
    version = 1,
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): BasketDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "basket_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
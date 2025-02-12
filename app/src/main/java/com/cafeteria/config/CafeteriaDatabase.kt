package com.cafeteria.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cafeteria.dao.FoodDao
import com.cafeteria.dao.UserDao
import com.cafeteria.data.food.FoodData
import com.cafeteria.entity.UserEntity


@Database(entities = [UserEntity::class, FoodData::class], version = 1, exportSchema = false)
abstract class CafeteriaDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun FoodDao(): FoodDao

    companion object {
        @Volatile
        private var Instance: CafeteriaDatabase? = null

        fun getDatabase(context: Context): CafeteriaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CafeteriaDatabase::class.java, "cafeteria_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }

    }

}
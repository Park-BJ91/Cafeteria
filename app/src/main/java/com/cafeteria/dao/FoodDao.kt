package com.cafeteria.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cafeteria.data.food.FoodData
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun foodInsert(foodData: FoodData)

    @Query("SELECT * FROM food_tbl")
    suspend fun foodAllList(): List<FoodData>
}
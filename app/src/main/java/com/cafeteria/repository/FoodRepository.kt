package com.cafeteria.repository

import com.cafeteria.data.food.FoodData
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun foodAllList(): List<FoodData>

    suspend fun foodInsert(foodData: FoodData)
}
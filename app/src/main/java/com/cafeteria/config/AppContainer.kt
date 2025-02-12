package com.cafeteria.config

import android.content.Context
import com.cafeteria.offline_repository.OffFoodRepository
import com.cafeteria.offline_repository.OffUserRepository
import com.cafeteria.repository.FoodRepository
import com.cafeteria.repository.UserRepository

interface AppContainer {
    val userRepository: UserRepository
    val foodRepository: FoodRepository
}

class AppDataContainer(private val context: Context) : AppContainer {


    override val userRepository: UserRepository by lazy {
        val db = CafeteriaDatabase.getDatabase(context)
        OffUserRepository(db.UserDao())
    }

    override val foodRepository: FoodRepository by lazy {
        val db = CafeteriaDatabase.getDatabase(context)
        OffFoodRepository(db.FoodDao())
    }
}
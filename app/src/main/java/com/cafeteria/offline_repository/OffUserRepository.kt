package com.cafeteria.offline_repository

import com.cafeteria.dao.FoodDao
import com.cafeteria.dao.UserDao
import com.cafeteria.data.food.FoodData
import com.cafeteria.entity.UserEntity
import com.cafeteria.repository.FoodRepository
import com.cafeteria.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class OffUserRepository(
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun userInsert(userEntity: UserEntity) = userDao.userInsert(userEntity)


//    override suspend fun foodAllList(): Flow<List<FoodData>> = foodDao.foodAllList()

}

class OffFoodRepository(
    private val foodDao: FoodDao
) : FoodRepository {
    override suspend fun foodInsert(foodData: FoodData) = foodDao.foodInsert(foodData)

    override suspend fun foodAllList(): List<FoodData> = foodDao.foodAllList()


}
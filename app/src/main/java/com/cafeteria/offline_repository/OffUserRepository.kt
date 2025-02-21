package com.cafeteria.offline_repository


import com.cafeteria.dao.UserDao
import com.cafeteria.entity.UserEntity
import com.cafeteria.repository.UserRepository

class OffUserRepository(
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun userInsert(userEntity: UserEntity) = userDao.userInsert(userEntity)

}

//class OffFoodRepository(
//    private val foodDao: FoodDao
//) : FoodRepository {
////    override suspend fun foodInsert(foodData: FoodData) = foodDao.foodInsert(foodData)
//
//    override suspend fun foodAllList(): List<FoodData> = foodDao.foodAllList()
//
//    override suspend fun foodTarget(id: Int): FoodData? = foodDao.foodTarget(id)
//
//
//}
package com.cafeteria.repository

import com.cafeteria.entity.UserEntity

interface UserRepository {

    suspend fun userInsert(userEntity: UserEntity)

}
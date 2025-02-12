package com.cafeteria.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.cafeteria.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun userInsert(userEntity: UserEntity)



}
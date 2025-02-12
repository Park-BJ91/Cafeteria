package com.cafeteria.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_list")
class UserEntity(
    @PrimaryKey
    val userId: String = "",
    val userName: String = "",
    val userNickname: String = "",
    val password: String = "",
    val email: String = "",

)


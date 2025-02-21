package com.cafeteria.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/* SQLite 에 List 데이터 넣을시
class DateListConverters {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value,Array<String>::class.java)?.toList()
    }
}
*/


@Entity(tableName = "user_list")
 data class UserEntity(
    @PrimaryKey
    val userId: String = "",
    val userName: String = "",
    val userNickname: String = "",
    val password: String = "",
    val email: String = "",

)


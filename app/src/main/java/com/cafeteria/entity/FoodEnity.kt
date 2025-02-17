package com.cafeteria.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_tbl")
data class FoodData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val img: ByteArray? = null
)

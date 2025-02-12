package com.cafeteria.data.food

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CustomDialogData(
    val title: String = "",
    val description: String = "",
    val onClickCancel: () -> Unit = {},
    val onClickConfirm: () -> Unit = {},
)

@Entity(tableName = "food_tbl")
data class FoodData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
//    val img: ByteArray
//    val img: ByteArray = ByteArray(300) {0}
)

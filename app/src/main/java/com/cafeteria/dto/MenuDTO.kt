package com.cafeteria.dto

import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    val id:Int = 0,
    val menuName: MenuName = MenuName(), // 메인 내용
    val description: String = "", // 설명
    val img: String = "", // 이미지 적용
    val menuDate: String = "", // 적용 날짜
    val mealTime: String = "중식"
)

@Serializable
data class MenuList(
    val menuList: List<Menu> = listOf()
)

@Serializable
data class MenuName(
    val main: String = "",
    val soup: String = "",
    val sideDish1: String = "",
    val sideDish2: String = "",
    val sideDish3: String = "",
    val sideDish4: String = "",
)

fun MenuName.isValid(): Boolean {
    return main != "" || soup != "" || sideDish1 != "" || sideDish2 != "" || sideDish3 != "" || sideDish4 != ""
}

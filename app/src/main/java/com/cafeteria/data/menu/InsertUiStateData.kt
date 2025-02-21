package com.cafeteria.data.menu


data class InsertUiStateData(
    val insertValidation: Boolean = false, // 메뉴 등록 유효성
    val insertResult: String = "", // 메뉴 등록 결과
    val pictureDialogState: Boolean = false, // 사진 Dialog ON / OFF
    val pictureImgData: ByteArray? = null // 선택 이미지
)

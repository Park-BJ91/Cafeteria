package com.cafeteria.data.dialog


// 기본 다이얼 로그
data class CustomDialogData(
    val title: String = "",
    val description: String = "",
    val onClickCancel: () -> Unit = {},
    val onClickConfirm: () -> Unit = {},
)

data class CustomDialogImageData(
    val title: String = "",
    val description: String = "",
    val onClickCancel: () -> Unit = {},
    val onClickGallery: (ByteArray) -> Unit = {},
    val onClickCamara: (ByteArray) -> Unit = {},
)


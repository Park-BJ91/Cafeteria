package com.cafeteria.data.dialog


data class MenuInsertDatePickerData(
    val selectedDate: String? = null,
    val isShowDialog: Boolean = false,
//    val onClickCancel: () -> Unit = {},
    val onClickConfirm: String? = null
)


/*
data class MenuInsertDatePickerData(
    val selectedDate: String? = null,
    val isShowDialog: Boolean = false,
    val onClickCancel: () -> Unit = {},
    val onClickConfirm: (String) -> Unit = {}
)
*/

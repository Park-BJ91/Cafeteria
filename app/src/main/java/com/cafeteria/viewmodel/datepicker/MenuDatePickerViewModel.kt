package com.cafeteria.viewmodel.datepicker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cafeteria.data.dialog.MenuInsertDatePickerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MenuDatePickerViewModel : ViewModel() {
    private val _menuDatePickerState = MutableStateFlow(MenuInsertDatePickerData())
    val menuDatePickerState = _menuDatePickerState

    // Date Dialog ON / OFF
    fun showMenuDatePrickerDialog(result: Boolean) {
        _menuDatePickerState.update {it.copy(isShowDialog = result)}
    }

    fun menuDateUpdate(yyyyMMdd: String, selectedDate: Long) {
        _menuDatePickerState.update {
            it.copy(
                onClickConfirm = yyyyMMdd,
                isShowDialog = false,
                selectedDate = selectedDate.toString()
            )
        }
    }

/*
    private val _menuDatePickerState = mutableStateOf(MenuInsertDatePickerData())
    val menuDatePickerState = _menuDatePickerState

    init {
        _menuDatePickerState.value = MenuInsertDatePickerData(
            onClickConfirm = {
                _menuDatePickerState.value = _menuDatePickerState.value.copy(
                    isShowDialog = false,
                    selectedDate = it
                )
            },
            onClickCancel = {
                _menuDatePickerState.value = _menuDatePickerState.value.copy(
                    isShowDialog = false
                )
            }
        )
    }

    fun showMenuDatePrickerDialog() {
        _menuDatePickerState.value = _menuDatePickerState.value.copy(isShowDialog = true)
    }
*/


}
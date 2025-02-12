package com.cafeteria.viewmodel.food

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.cafeteria.data.food.CustomDialogData
import com.cafeteria.data.food.FoodData
import com.cafeteria.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


//class FoodViewModel(private val repository: FoodRepository) : ViewModel() {
class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _dialogState = MutableStateFlow(CustomDialogData())
    val dialogState = _dialogState

    private val _dialogBoolean = MutableStateFlow(false)
    val dialogBoolean = _dialogBoolean

    // 음식 데이터
    private val _foodState = MutableStateFlow(FoodData())
    val foodState = _foodState


    val pictureState = MutableStateFlow(false)

    // 메뉴 데이터 등록
    suspend fun foodInsert(t: String,d: String) {
        _foodState.value = FoodData(
            title = t,
            description = d
        )
        repository.foodInsert(_foodState.value)
    }

    init {
        pictureState.value = false
        Log.d("xxx", "ViewModel Init :: $pictureState")
    }

    fun pictureBoolean() {
        pictureState.value = true
    }

    fun showDialogOne() {
        _dialogState.update {
            it.copy(
                title = "메뉴 등록",
                description = "메뉴 등록 페이지 이동",
                onClickCancel = {
                    resetState()
                },
                onClickConfirm = {
                    confirmMove()
                }
            )

        }
        _dialogBoolean.value = true

    }


    private fun confirmMove() {
        _dialogState.value = CustomDialogData()
        _dialogBoolean.value = false
    }


    private fun resetState() {
        _dialogState.value = CustomDialogData()
        _dialogBoolean.value = false

    }

}
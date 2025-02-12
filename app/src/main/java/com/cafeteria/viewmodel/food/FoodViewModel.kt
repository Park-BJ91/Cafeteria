package com.cafeteria.viewmodel.food

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.cafeteria.data.food.CustomDialogData
import com.cafeteria.data.food.CustomDialogImageData
import com.cafeteria.data.food.FoodData
import com.cafeteria.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    // 기존 다이얼 로그
    private val _dialogState = MutableStateFlow(CustomDialogData())
    val dialogState = _dialogState

    // 기존 다이얼 로그 상태
    private val _dialogBoolean = MutableStateFlow(false)
    val dialogBoolean = _dialogBoolean

    // 음식 데이터
    private val _foodState = MutableStateFlow(FoodData())
    val foodState = _foodState


    // 이미지 등록 다이얼 로그
    private val _pictureState = MutableStateFlow(CustomDialogImageData())
    val pictureState = _pictureState
    // 이미지 등록 다이얼 로그 상태
    private val _pictureBoolean = MutableStateFlow(false)
    val pictureBoolean = _pictureBoolean

    private val _pictureImg = MutableStateFlow<ByteArray?>(null)
    val pictureImg = _pictureImg


    // 이미지 등록 다이얼 로그
    fun showDialogOneImage() {
        _pictureState.update {
            it.copy(
                title = "이미지 등록 선택",
                description = "취소 하려면 범위 밖을 누르시오",
                onClickCancel = {
                    dialogImgReset()
                },
                onClickGallery = { image ->
                    galleryFun(image)
                },
                onClickCamara = { image ->
                    cameraFun(image)
                }
            )
        }
        _pictureBoolean.value = true
    }

    private fun galleryFun(imgData: ByteArray) {
        Log.d("www", " Gallery :: $imgData")
        pictureImg.value = imgData
        _pictureState.value = CustomDialogImageData()
        _pictureBoolean.value = false
    }

    private fun cameraFun(imgData: ByteArray) {
        Log.d("www", " Camara :: $imgData")
        pictureImg.value = imgData
        _pictureState.value = CustomDialogImageData()
        _pictureBoolean.value = false
    }

    private fun dialogImgReset() {
        Log.d("www", " Cancel")
        _pictureState.value = CustomDialogImageData()
        _pictureBoolean.value = false
    }





    // 메뉴 데이터 등록
    suspend fun foodInsert(t: String,d: String) {
        _foodState.value = FoodData(
            title = t,
            description = d
        )
        repository.foodInsert(_foodState.value)
    }


    // 기본 다이얼 로그
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
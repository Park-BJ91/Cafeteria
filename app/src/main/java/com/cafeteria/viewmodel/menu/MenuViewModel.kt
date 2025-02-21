package com.cafeteria.viewmodel.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeteria.data.dialog.CustomDialogImageData
import com.cafeteria.dto.Menu
import com.cafeteria.dto.MenuName
import com.cafeteria.dto.isValid
import com.cafeteria.retrofit.inferface.RetrofitClient
import com.cafeteria.data.menu.InsertUiStateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuViewModel : ViewModel() {

    private val tag = "insert menu"

    /*    // 기존 다이얼 로그
    private val _dialogState = MutableStateFlow(CustomDialogData())
    val dialogState = _dialogState

    // 기존 다이얼 로그 상태
    private val _dialogBoolean = MutableStateFlow(false)
    val dialogBoolean = _dialogBoolean */

    /*
    // 이미지 등록 다이얼 로그 상태
    private val _pictureBoolean = MutableStateFlow(false)
    val pictureBoolean = _pictureBoolean

    // 이미지
    private val _pictureImg = MutableStateFlow<ByteArray?>(null)
    val pictureImg = _pictureImg
*/

    /*    // 메뉴 등록 유효성
    private val _menuStateBoolean = MutableStateFlow(false)
    val menuStateBoolean = _menuStateBoolean

    // 메뉴 등록 결과
    private val _menuInsertResult = MutableStateFlow("")
    val menuInsertResult = _menuInsertResult*/

    // 메뉴 데이터
    private val _menuState = MutableStateFlow(Menu())
    val menuState = _menuState

    // 이미지 등록 다이얼 로그 설정
    private val _pictureState = MutableStateFlow(CustomDialogImageData())
    val pictureState = _pictureState

    private val _uiState = MutableStateFlow(InsertUiStateData())
    val uiState = _uiState

    // 이미지 등록 다이얼 로그
    fun showDialogOneImage() {
        _pictureState.update {
            // ## 앨범,카메라 함수 통합
            it.copy(title = "이미지 등록 선택", description = "취소 하려면 범위 밖을 누르시오", onClickCancel = {
                dialogImgReset()
            }, onClickGallery = { image ->
                pictureUpdate(image)
            }, onClickCamara = { image ->
                pictureUpdate(image)
            })
        }
        _uiState.update { it.copy(pictureDialogState = true) }
    }

    private fun pictureUpdate(imgData: ByteArray) {
        _uiState.update { it.copy(pictureDialogState = false) }
        _uiState.update { it.copy(pictureImgData = imgData) }
        _pictureState.value = CustomDialogImageData()
        _menuState.update { it.copy(img = imgData) }
    }


    private fun dialogImgReset() {
        _pictureState.value = CustomDialogImageData()
        _uiState.update { it.copy(pictureDialogState = false) }
    }

    /************************************
    // 메뉴 데이터 등록 (Room)
    suspend fun foodInsert(t: String, d: String, i: ByteArray?) {
    _menuState.value = FoodData(
    title = t,
    description = d,
    img = i
    )
    repository.foodInsert(_menuState.value)
    }
     */
    // 라디오 버튼
    fun foodType(type: String) {
        var intType = 0
        intType = if (type == "중식") 0 else 1

        _menuState.update {
            it.copy(
                mealTime = intType
            )
        }
    }

    // 날짜 업로드
    fun foodDateUpdate(date: String) {
        _menuState.update {
            it.copy(
                menuDate = date
            )
        }
    }

    // 음식 메뉴 데이터 등록
    suspend fun foodInsert() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(tag, "$$$$$ ${_menuState.value}")
                val result = RetrofitClient.menuService.foodInsert(_menuState.value)
                if (result.isSuccessful) {
                    val jsonStr = result.body()
                    Log.d(tag, "####### Success  $jsonStr")
                    _uiState.update { it.copy(insertResult = "true") }

                } else if (result.code() == 409) {
                    Log.d(tag, "####### Duplicate")
                    _uiState.update { it.copy(insertResult = "duplicate") }
                } else {
                    Log.d(tag, "####### Other ${result.code()}")
                    _uiState.update { it.copy(insertResult = "false") }
                }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    Log.d(tag, "####### Catch ${e.message}")
                    _uiState.update { it.copy(insertResult = "false") }
                }
            }
        }
    }

    fun insertResultReset() {
        _uiState.update { it.copy(insertResult = "") }
    }

    // 음식 메뉴 등록전 체크 && menuList 업데이트
    fun foodInsertCheck(menuName: MenuName, description: String): Boolean {
        val menusCheck = menuName.isValid()
        if (menusCheck) {
            _menuState.update {
                it.copy(
                    menuName = menuName,
                    description = description
                )
            }
        }
        return with(_menuState.value) { menuDate.isNotEmpty() && menusCheck }
    }

    // 메뉴 유효성 결과
    fun insertValidationResult(result: Boolean) {
        _uiState.update { it.copy(insertValidation = result) }
    }


}
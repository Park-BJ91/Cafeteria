package com.cafeteria.viewmodel.menu

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeteria.R
import com.cafeteria.data.dialog.CustomDialogImageData
import com.cafeteria.dto.Menu
import com.cafeteria.dto.MenuName
import com.cafeteria.dto.isValid
import com.cafeteria.retrofit.inferface.RetrofitClient
import com.cafeteria.data.menu.MenuUiStateData
import com.cafeteria.dto.MenuList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response


class MenuViewModel : ViewModel() {

    private val tag = "menuViewModel"

    // 메뉴 데이터
    private val _menuState = MutableStateFlow(Menu())
    val menuState = _menuState

    // 메뉴 목록
    private val _menuListState = MutableStateFlow(MenuList())
    val menuListState = _menuListState

    // 이미지 등록 다이얼 로그 설정
    private val _pictureState = MutableStateFlow(CustomDialogImageData())
    val pictureState = _pictureState

    // 동작 UI
    private val _uiState = MutableStateFlow(MenuUiStateData())
    val uiState = _uiState

    // 등록 에서 수정 페이지 모드 여부
    private val _uiEditState = MutableStateFlow(false)
    val uiEditState = _uiEditState

    // 메뉴 입력 값
    private val _menuNames = MutableStateFlow(MenuName())
    val menuNames = _menuNames


    /* ========== Menu Insert ========== */

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

        val base64 = byteArrToBase64(imgData)
        _menuState.update { it.copy(img = base64) }
    }

    // 이미지 Base64인코딩
    private fun byteArrToBase64(imgData: ByteArray): String {
        return Base64.encodeToString(imgData, Base64.NO_WRAP)

    }

    // 카메라 ,앨범 다이얼 로그 초기화
    private fun dialogImgReset() {
        _pictureState.value = CustomDialogImageData()
        _uiState.update { it.copy(pictureDialogState = false) }
    }

    /************************************
    // 메뉴 데이터 등록 (Room)
    suspend fun menuInsert(t: String, d: String, i: ByteArray?) {
    _menuState.value = menuData(
    title = t,
    description = d,
    img = i
    )
    repository.menuInsert(_menuState.value)
    }
     */
    // 라디오 버튼
    fun menuType(type: String) {
        _menuState.update {
            it.copy(
                mealTime = type
            )
        }
    }

    // 날짜 업로드
    fun menuDateUpdate(date: String) {
        _menuState.update {
            it.copy(
                menuDate = date
            )
        }
    }

    // 메뉴 텍스트 필드 Update
    fun menuNameUpdate(selected: String, name: String) {
        _menuNames.update {
            when (selected) {
                "메인" -> it.copy(main = name)
                "국" -> it.copy(soup = name)
                "반찬1" -> it.copy(sideDish1 = name)
                "반찬2" -> it.copy(sideDish2 = name)
                "반찬3" -> it.copy(sideDish3 = name)
                "반찬4" -> it.copy(sideDish4 = name)
                else -> return
            }
        }
    }

    // 설명 텍스트 필드 Update
    fun menuDescriptionUpdate(name: String) {
        _menuState.update { it.copy(description = name) }
    }


    // 메뉴 등록 && 메뉴 수정
    suspend fun menuInsert(selected: Int) {
        try {
            when (selected) {
                0 -> {
                    val result = RetrofitClient.menuService.menuInsert(_menuState.value)
                    menuProcessResult(
                        result,
                        R.string.menu_request_success,
                        R.string.menu_request_duplicate
                    )
                }

                else -> {
                    val result = RetrofitClient.menuService.menuEdit(_menuState.value)
                    menuProcessResult(
                        result,
                        R.string.menu_request_success,
                        R.string.menu_request_duplicate
                    )
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _uiState.update {
                    it.copy(
                        result = "false",
                        stringRes = R.string.menu_request_fail
                    )
                }
            }
        }
    }

    /**
     * [menuInsert]
     * */
    private fun menuProcessResult(
        result: Response<String>,
        successResId: Int,
        duplicateResId: Int
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            if (result.isSuccessful) {
                _uiState.update { it.copy(result = "true", stringRes = successResId) }
            } else if (result.code() == 409) {
                _uiState.update { it.copy(result = "duplicate", stringRes = duplicateResId) }
            } else {
                _uiState.update {
                    it.copy(
                        result = "false",
                        stringRes = R.string.menu_request_fail
                    )
                }
            }
        }
    }

    // 등록 결과 Toast 초기화
    fun resultReset() {
        _uiState.update { it.copy(result = "") }
    }

    // 음식 메뉴 등록전 체크 && menuList 업데이트
    fun menuInsertCheck(): Boolean {
        _menuState.update { it.copy(menuName = _menuNames.value) }
        val menusCheck = _menuState.value.menuName.isValid()
        return with(_menuState.value) { menuDate.isNotEmpty() && menusCheck }
    }

    // 메뉴 유효성 결과
    fun insertValidationResult(result: Boolean) {
        _uiState.update { it.copy(validation = result) }
    }
    /* ########## Menu Insert END ########## */


    /* ========== Menu Select(MenuScreen) ========== */

    // 음식 메뉴 전체 조회
    suspend fun menuSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = RetrofitClient.menuService.menuSearch()
                if (response.isSuccessful) {
                    val list = response.body()
                    _menuListState.update {
                        it.copy(menuList = list!!)
                    }
                }
                _uiState.update { it.copy(loading = "true") }
            } catch (e: Exception) {
                Log.d(tag, "############### 메뉴 조회 실패... ${e.message}")
                _uiState.update { it.copy(loading = "error") }
            }
        }
    }

    /* ########## Menu Select(MenuScreen) END ########## */

    /* ========== Menu Detail ========== */
    // 음식 상세 정보 조회
    suspend fun menuDetailSearch(menuId: Int) {
        try {
            val response = RetrofitClient.menuService.menuDetail(menuId)
            if (response.isSuccessful) {
                val menuData = response.body()
                val menuName = menuData?.menuName
                _menuNames.value = menuName ?: MenuName()
                _menuState.update {
                    it.copy(
                        id = menuData!!.id,
                        img = menuData.img,
                        menuName = _menuNames.value,
                        description = menuData.description,
                        mealTime = menuData.mealTime,
                        menuDate = menuData.menuDate
                    )
                }
                viewModelScope.launch(Dispatchers.Main) {
                    _uiEditState.update { true }
                }
            }

        } catch (e: Exception) {
            Log.d(tag, "######### 메뉴 상세 정보 조회 실패 ${e.message} ")
        }


    }

    // 메인 메뉴 조회(Main)
    suspend fun menuDetailTarget(date: String) {
        try {
            _uiState.update { it.copy(loading = "false") }
            val response = RetrofitClient.menuService.menuMainList(date)
            if (response.isSuccessful) {
                val list: List<Menu> = response.body()!!
                _menuListState.update {
                    it.copy(
                        menuList = list
                    )
                }
                _uiState.update { it.copy(loading = "true") }
                if (list.isEmpty()) {
                    _uiState.update { it.copy(loading = "empty") }
                }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(loading = "fail") }
            Log.d(tag, "실패")
        }
    }


    suspend fun menuDelete(menuId: Int) {
        try {
            val response = RetrofitClient.menuService.menuDelete(menuId)
            if (response.isSuccessful) {
                response.body()!!.let { boolean ->
                    if (boolean) {
                        _uiState.update {
                            it.copy(
                                result = "true",
                                stringRes = R.string.menu_delete_success
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                result = "false",
                                stringRes = R.string.menu_delete_fail
                            )
                        }
                    }
                }


            }
        } catch (e: Exception) {
            Log.d(tag, "########## 메뉴 삭제 실패 ${e.message}")
        }
    }

}
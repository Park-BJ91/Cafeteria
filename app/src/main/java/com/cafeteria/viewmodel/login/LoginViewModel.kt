package com.cafeteria.viewmodel.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _textName = MutableStateFlow("")
    val textName = _textName

    private val _textId = MutableStateFlow("")
    val textId = _textId

    private val _textPwd = MutableStateFlow("")
    val textPwd = _textPwd

    fun textNameUpdate(value: String) {
        _textName.update { value }
    }
    fun textIdUpdate(value: String) {
        _textId.update { value }
    }
    fun textPwdUpdate(value: String) {
        _textPwd.update { value }
    }


}
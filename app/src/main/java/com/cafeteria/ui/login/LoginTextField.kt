package com.cafeteria.ui.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

// 로그인, 회원가입 텍스트 핖드
@Composable
fun TextFieldTypeNumEng(
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    )
}

@Composable
fun TextFieldTypePassWord(
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder)  },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    )
}

@Composable
fun TextFieldTypeDefault(
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder)  },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    )
}

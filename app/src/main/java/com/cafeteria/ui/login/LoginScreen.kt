package com.cafeteria.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cafeteria.destination.InterfaceNavDestination
import com.cafeteria.viewmodel.login.LoginViewModel

object LoginDestination : InterfaceNavDestination {
    override val route: String = "login"
    override val titleRes: Int = 0
}

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onClickJoin: () -> Unit,
) {

    var textId by remember { mutableStateOf("") }
    var textPwd by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
        ) {
            TextFieldTypeNumEng(
                textValue = textId,
                onValueChange = { },
                placeholder = "아이디 (5 ~ 12자)"
            )

            TextFieldTypePassWord(
                textValue = textPwd,
                onValueChange = { },
                placeholder = "비밀번호 (5 ~ 12자)"
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text("로그인")
            }

            Text(
                text = "회원가입",
                modifier = Modifier
                    .padding(start = 8.dp, top = 26.dp)
                    .clickable(
                        onClick = onClickJoin
                    )
            )

        }

    }
}
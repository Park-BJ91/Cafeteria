package com.cafeteria.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cafeteria.viewmodel.login.LoginViewModel

@Composable
fun JoinScreen(
    onClickBack: () -> Unit,
    onClickJoin: () -> Unit,
    loginViewModel: LoginViewModel = view
) {

    Scaffold(
        topBar = {
            JoinTopBar(
                onClickBack = onClickBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 20.dp)
        ) {
            TextFieldTypeDefault(
                textValue = "",
                onValueChange = {},
                placeholder = "이름"
            )

            TextFieldTypeNumEng(
                textValue = "",
                onValueChange = {},
                placeholder = "아이디"
            )

            TextFieldTypePassWord(
                textValue = "",
                onValueChange = {},
                placeholder = "비밀번호"
            )

            Button(
                onClick = onClickJoin,
                contentPadding = PaddingValues(20.dp),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("회원가입")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinTopBar(
    onClickBack: () -> Unit,
) {

    CenterAlignedTopAppBar(
        title = {
            Row() {
                Text("회원가입")
            }
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "back"
                )
            }
        }
    )

}
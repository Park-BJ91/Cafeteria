package com.cafeteria.ui.menu

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.cafeteria.data.menu.MenuUiStateData
import com.cafeteria.viewmodel.menu.MenuViewModel

@Composable
fun MenuResultToast(
    menuUiState: MenuUiStateData,
    menuViewModel: MenuViewModel,
    context: Context = LocalContext.current,
    backOnClick: () -> Unit = {},

) {

    // 결과 Toast
    when (menuUiState.result) {
        "true" -> {
            Toast.makeText(context, stringResource(menuUiState.stringRes) , Toast.LENGTH_SHORT).show()
            backOnClick()
        }

        "false" -> {
            Toast.makeText(context, stringResource(menuUiState.stringRes) , Toast.LENGTH_SHORT).show()
            menuViewModel.resultReset()
        }

        "duplicate" -> {
            Toast.makeText(context, stringResource(menuUiState.stringRes) , Toast.LENGTH_SHORT).show()
            menuViewModel.resultReset()
        }
    }


}
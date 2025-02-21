package com.cafeteria.ui.menu

import android.view.Menu
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafeteria.R
import com.cafeteria.ui.navigation.top.MenuTopAppBar
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider

// 네비 경로는 바텀 네비에 있어서 설정 X
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory),
    paddingValues: PaddingValues,
    menuAddOnClick: () -> Unit,
    menuDetailOnClick: () -> Unit
) {
    val menuState by menuViewModel.menuState.collectAsState()
    val topBarScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(topBarScroll.nestedScrollConnection)
            .padding(),
        topBar = {
            MenuTopAppBar(
                title = stringResource(R.string.menu_title),
                back = false,
                topBarScroll = topBarScroll,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = menuAddOnClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding( end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "ADD Menu"
                )
            }
        }
    ) {
        it
        MenuColumnList(
//            menuList =
        )
    }

}

@Composable
fun MenuColumnList(
//    menuList: List<Menu>
) {

}



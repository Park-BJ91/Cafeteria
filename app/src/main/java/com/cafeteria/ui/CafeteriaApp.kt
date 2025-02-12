package com.cafeteria.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.ui.navigation.CafeteriaNavHost
import com.cafeteria.viewmodel.navigation.BottomNavViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CafeteriaApp(
    navController: NavHostController = rememberNavController(),
    bottomNavViewModel: BottomNavViewModel = viewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedScreen by bottomNavViewModel.bottomNavSelected.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = { navBackStackEntry?.destination?.route },
                viewModel = bottomNavViewModel,
            )
        }
    ) {
        CafeteriaNavHost(navController, it)
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: () -> String?,
    viewModel: BottomNavViewModel,

    ) {
    // 바텀 네비 목록
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Reservation,
        BottomNavItem.Board,
        BottomNavItem.Settings,
        BottomNavItem.Insert
    )

    BottomAppBar {
        screens.forEach { screen ->
            AddItem(
                screen = { screen },
                currentDestination = currentRoute,
                navController = navController,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: () -> BottomNavItem,
    currentDestination: () -> String?,
    navController: NavHostController,
    viewModel: BottomNavViewModel,
) {

    NavigationBarItem(
        label = { Text(text = stringResource(screen().title)) },
        icon = {
            Icon(
                imageVector = screen().icon,
                contentDescription = "Bottom Nav",
                tint = if (screen().route == currentDestination()) Color.LightGray else Color.DarkGray
            )
        },
        alwaysShowLabel = true,
        selected = false,
        onClick = {
            navController.navigate(screen().route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id)
            }
        }
    )
}



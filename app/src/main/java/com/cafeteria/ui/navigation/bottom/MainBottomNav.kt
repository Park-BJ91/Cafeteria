package com.cafeteria.ui.navigation.bottom

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.viewmodel.navigation.BottomNavViewModel

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: () -> String?,
    ) {
    // 바텀 네비 목록
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Reservation,
        BottomNavItem.Board,
        BottomNavItem.Settings,
        BottomNavItem.Menu
    )

    BottomAppBar {
        screens.forEach { screen ->
            AddItem(
                screen = { screen },
                currentDestination = currentRoute,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: () -> BottomNavItem,
    currentDestination: () -> String?,
    navController: NavHostController,
) {

    NavigationBarItem(
        label = { Text(text = stringResource(screen().title)) },
        icon = {
            Icon(
                imageVector = screen().icon,
                contentDescription = "Bottom Nav",
                tint = if (screen().route == currentDestination()) MaterialTheme.colorScheme.primary else Color.DarkGray,
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
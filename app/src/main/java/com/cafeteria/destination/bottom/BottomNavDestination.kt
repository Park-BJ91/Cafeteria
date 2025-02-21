package com.cafeteria.destination.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cafeteria.R

sealed class BottomNavItem(
    val route: String,
    val title: Int,
    val icon: ImageVector,
) {
    data object Home: BottomNavItem(
        route = R.string.home.toString(),
        title = R.string.home_title,
        icon = Icons.Outlined.Home,
    )
    data object Reservation: BottomNavItem(
        route = R.string.reservation.toString(),
        title = R.string.reservation_title,
        icon = Icons.Outlined.BrowseGallery,
    )
    data object Board: BottomNavItem(
        route = R.string.board.toString(),
        title = R.string.board_title,
        icon = Icons.Outlined.AutoStories,
    )
    data object Settings: BottomNavItem(
        route = R.string.settings.toString(),
        title = R.string.settings_title,
        icon = Icons.Outlined.Settings,
    )
    data object Menu: BottomNavItem(
        route = R.string.menu.toString(),
        title = R.string.menu_title,
        icon = Icons.Outlined.Backpack
    )
}



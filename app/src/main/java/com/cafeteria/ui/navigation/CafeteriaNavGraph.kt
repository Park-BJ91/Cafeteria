package com.cafeteria.ui.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.ui.board.BoardScreen
import com.cafeteria.ui.home.HomeScreen
import com.cafeteria.ui.insert.InsertScreen
import com.cafeteria.ui.reservation.ReservationScreen
import com.cafeteria.ui.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CafeteriaNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    NavHost(
        navController = navController, startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Reservation.route) {
            ReservationScreen()
        }
        composable(BottomNavItem.Board.route) {
            BoardScreen()
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen()
        }
        composable(BottomNavItem.Insert.route) {
            InsertScreen(navController)
        }

    }

}

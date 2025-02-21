package com.cafeteria.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.ui.board.BoardScreen
import com.cafeteria.ui.home.HomeScreen
import com.cafeteria.ui.menu.MenuEntryDestination
import com.cafeteria.ui.menu.MenuEntryScreen
import com.cafeteria.ui.menu.MenuScreen
import com.cafeteria.ui.navigation.bottom.BottomBar
import com.cafeteria.ui.reservation.ReservationScreen
import com.cafeteria.ui.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CafeteriaNavHost(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController, startDestination = BottomNavItem.Home.route,
    ) {

        // 메인
        composable(BottomNavItem.Home.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        currentRoute = { navBackStackEntry?.destination?.route },

                    )
                }
            ) { paddingValues ->
                HomeScreen(paddingValues)
            }
        }
        // 예약
        composable(BottomNavItem.Reservation.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        currentRoute = { navBackStackEntry?.destination?.route },

                        )
                }
            ) { paddingValues ->
                ReservationScreen(paddingValues)

            }
        }
        // 게시판
        composable(BottomNavItem.Board.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        currentRoute = { navBackStackEntry?.destination?.route },

                        )
                }
            ) { paddingValues ->
                BoardScreen(paddingValues)

            }
        }
        // 설정
        composable(BottomNavItem.Settings.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        currentRoute = { navBackStackEntry?.destination?.route },

                        )
                }
            ) { paddingValues ->
                SettingsScreen(paddingValues)

            }
        }
        // 관리자 메뉴 관리
        composable(BottomNavItem.Menu.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        currentRoute = { navBackStackEntry?.destination?.route },

                        )
                }
            ) { paddingValues ->
                MenuScreen(
                    paddingValues = paddingValues,
                    menuAddOnClick = { navController.navigate(MenuEntryDestination.route)},
                    menuDetailOnClick = {}
                )
            }

        }
        composable(MenuEntryDestination.route) {
                MenuEntryScreen(
                    backOnClick = {navController.navigate(BottomNavItem.Menu.route)}
                )
        }

    }

}

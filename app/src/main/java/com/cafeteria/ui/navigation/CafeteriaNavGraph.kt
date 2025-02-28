package com.cafeteria.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.ui.board.BoardScreen
import com.cafeteria.ui.home.HomeScreen
import com.cafeteria.ui.login.JoinScreen
import com.cafeteria.ui.login.LoginDestination
import com.cafeteria.ui.login.LoginScreen
import com.cafeteria.ui.menu.MenuDetailDestination
import com.cafeteria.ui.menu.MenuDetailScreen
import com.cafeteria.ui.menu.MenuInsertDestination
import com.cafeteria.ui.menu.MenuInsertScreen
import com.cafeteria.ui.menu.MenuScreen
import com.cafeteria.ui.navigation.bottom.BottomBar
import com.cafeteria.ui.reservation.ReservationScreen
import com.cafeteria.ui.settings.SettingsScreen
import com.cafeteria.viewmodel.login.LoginViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CafeteriaNavHost(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController, startDestination = LoginDestination.route,
    ) {

        composable(LoginDestination.route) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onClickJoin = { navController.navigate("join") }
            )
        }

        composable("join") {
            JoinScreen(
                onClickBack = { navController.navigate(LoginDestination.route) },
                onClickJoin = { navController.navigate(BottomNavItem.Menu.route)}
            )
        }

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
                        currentRoute = { navBackStackEntry?.destination?.route }
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
                    menuAddOnClick = { navController.navigate("${MenuInsertDestination.route}/$it") },
                    menuDetailOnClick = { navController.navigate("${MenuDetailDestination.route}/$it") },
                    menuEditOnClick = { navController.navigate("${MenuInsertDestination.route}/$it") },
                    menuDeleteOnClick = { navController.navigate(BottomNavItem.Menu.route) }
                )
            }
        }
        // 메뉴 등록
        composable(
            route = MenuInsertDestination.routeArgs,
            arguments = listOf(navArgument("division") {
                type = NavType.IntType
            })
        ) {
            val param = it.arguments!!.getInt(MenuInsertDestination.param)
            MenuInsertScreen(
                division = param,
                backOnClick = { navController.navigate(BottomNavItem.Menu.route) }
            )
        }
        // 메뉴 상세
        composable(
            route = MenuDetailDestination.routeArgs,
            arguments = listOf(navArgument(MenuDetailDestination.params) {
                type = NavType.IntType
            })
        ) {
            val id = it.arguments!!.getInt(MenuDetailDestination.params)
            MenuDetailScreen(
                menuId = id,
                //
                onClickEdit = { navController.navigate("수정") },
                onClickDelete = { navController.navigate(BottomNavItem.Menu.route) },
            )
        }
        // 메뉴 수정
        composable(
            route = "수정"
        ) {


        }

    }

}

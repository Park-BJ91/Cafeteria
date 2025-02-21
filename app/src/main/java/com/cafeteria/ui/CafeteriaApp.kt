package com.cafeteria.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cafeteria.ui.navigation.CafeteriaNavHost

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CafeteriaApp(
    navController: NavHostController = rememberNavController(),
) {
    CafeteriaNavHost(navController)

}






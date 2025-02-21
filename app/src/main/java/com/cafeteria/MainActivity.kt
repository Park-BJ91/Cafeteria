package com.cafeteria


import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.cafeteria.ui.CafeteriaApp
import com.cafeteria.ui.theme.CafeteriaTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            val configuration = LocalConfiguration.current
//            val context = LocalContext.current
//            val resources = context.resources
//            val displayMetrics = resources.displayMetrics
//            val config = Configuration(configuration)
//            config.setLocale(Locale("ko", "KR"))
//            resources.updateConfiguration(config, displayMetrics)

            CafeteriaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CameraPermissionRequester { }
                    CafeteriaApp()
                }

            }
        }
    }

}

@Composable
fun CameraPermissionRequester(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    var showPermission by remember { mutableStateOf(true) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionGranted()
                showPermission = false
            } else {
                // 권한이 거부된 경우 처리
                Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    if (showPermission) {
        LaunchedEffect(true) {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

}

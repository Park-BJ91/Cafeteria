package com.cafeteria.ui.insert

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cafeteria.CameraPermissionRequester
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.viewmodel.food.FoodViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun InsertScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    var titleValue by remember { mutableStateOf("") }
    var descriptionValue by remember { mutableStateOf("") }

    val selectedChooser = foodViewModel.pictureState.collectAsState().value
    Log.d("xxx", "VALUE ::  $selectedChooser")

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Button(
                onClick = {
                    foodViewModel.pictureBoolean()

                }
            ) {
                Text("이미지 업로드 선택")
            }
            if (selectedChooser) {
                ImagePicker(
                    selectedChooser = selectedChooser
                ) {
                    it
                }
            }
            OutlinedTextField (
                value = titleValue,
                onValueChange = {
                    titleValue = it
                },
                label = {
                    Text("title")
                },
                singleLine = true,
            )

            OutlinedTextField (
                value = descriptionValue,
                onValueChange = {
                    descriptionValue = it
                },
                label = {
                    Text("description")
                },
                singleLine = true
            )

            Button(
                onClick =  {
                    coroutineScope.launch {
                        foodViewModel.foodInsert(titleValue,descriptionValue)
                        navController.navigate(BottomNavItem.Settings.route)
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }


/*
    val viewModelState = viewModel.dialogState.collectAsState()
    val viewModelCheck = viewModel.dialogBoolean.collectAsState()

    Button(
        onClick = { viewModel.showDialogOne()  }
    ) {
        Text("Dialog")
    }

    if (viewModelCheck.value) {
        MenuInsertDialog(
            title = viewModelState.value.title,
            description = viewModelState.value.description,
            onClickCancel = { viewModelState.value.onClickCancel() },
            onClickConfirm = {
                viewModelState.value.onClickConfirm()
                navController.navigate(BottomNavItem.Reservation.route)
            }
        )
    }
*/

}

@Composable
fun ImagePicker(
    selectedChooser: Boolean,
    onImageSelected: (ByteArray) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraPermission by remember { mutableStateOf(false) }

    if (!cameraPermission) {
        CameraPermissionRequester {
            cameraPermission = true
        }
        return
    }

    // 갤러리에서 이미지 선택
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                val inputStream = context.contentResolver.openInputStream(it)
                val imageBytes = inputStream?.readBytes()
                if (imageBytes != null) {
                    onImageSelected(imageBytes)
                }
            }
        }
    )

    // 카메라로 사진 찍기
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && imageUri != null) {
                val inputStream = context.contentResolver.openInputStream(imageUri!!)
                val imageBytes = inputStream?.readBytes()
                if (imageBytes != null) {
                    onImageSelected(imageBytes)
                }
            }
        }
    )

    // 이미지 선택 옵션 (갤러리 또는 카메라)
//    LaunchedEffect(selectedChooser) {
        val options = arrayOf("갤러리에서 선택", "카메라로 찍기")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("이미지 선택")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> galleryLauncher.launch("image/*")
                1 -> {
                    val file = createImageFile(context)
                    imageUri = getImageUri(context, file)
                    cameraLauncher.launch(imageUri!!)
                }
            }
        }
        builder.show()
//    }

}

fun createImageFile(context : Context): File  {
    val storageDir = context.cacheDir // 캐시 디렉토리 사용
    return File.createTempFile(
        "temp_image_", // 파일 이름 접두사
        ".jpg", // 파일 확장자
        storageDir // 저장 디렉토리
    )
}

fun getImageUri(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider", // FileProvider 권한
        file
    )
}


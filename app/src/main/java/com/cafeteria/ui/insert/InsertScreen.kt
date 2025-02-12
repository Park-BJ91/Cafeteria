package com.cafeteria.ui.insert

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cafeteria.CameraPermissionRequester
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.ui.dialog.meun.MenuInsertDialog
import com.cafeteria.ui.dialog.meun.MenuInsertDialogImage
import com.cafeteria.viewmodel.food.FoodViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun InsertScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    // 텍스트 입력 데이터
    var titleValue by remember { mutableStateOf("") }
    var descriptionValue by remember { mutableStateOf("") }

    // 기존 다이얼 로그
    val viewModelState by foodViewModel.dialogState.collectAsState()
    val viewModelCheck by foodViewModel.dialogBoolean.collectAsState()

    val pictureState by foodViewModel.pictureState.collectAsState()
    val pictureBoolean by foodViewModel.pictureBoolean.collectAsState()
    val pictureImg by foodViewModel.pictureImg.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    // 이미지 또는 카메라에서 뒤로 돌아 갔을시 Dialog 남아있는 문제

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            if (pictureBoolean) {
                MenuInsertDialogImage(
                    title = pictureState.title,
                    description = pictureState.description,
                    onClickCancel = pictureState.onClickCancel,
                    onClickGallery = pictureState.onClickGallery,
                    onClickCamara = pictureState.onClickCamara
                )
            }

            Button(
                onClick = { foodViewModel.showDialogOneImage() }
            ) {
                Text(text = "사진 업로드")
            }

            // ########## 값 유지 때문에 무한 루프 에러남 !!!!
            if (pictureImg != null) {
                val imageBitmap = remember {
                    BitmapFactory.decodeByteArray(pictureImg, 0, pictureImg!!.size).asImageBitmap()
                }
                Image(bitmap = imageBitmap, contentDescription = "Loaded Image")
            }

            OutlinedTextField(
                value = titleValue,
                onValueChange = {
                    titleValue = it
                },
                label = {
                    Text("title")
                },
                singleLine = true,
            )

            OutlinedTextField(
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
                onClick = {
                    coroutineScope.launch {
                        foodViewModel.foodInsert(titleValue, descriptionValue)
                        navController.navigate(BottomNavItem.Settings.route)
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }




//        val viewModelState by foodViewModel.dialogState.collectAsState()
//        val viewModelCheck by foodViewModel.dialogBoolean.collectAsState()

    /*        Button(
                onClick = { foodViewModel.showDialogOne()  }
            ) {
                Text("Dialog")
            }

            if (viewModelCheck) {
                MenuInsertDialog(
                    title = viewModelState.title,
                    description = viewModelState.description,
                    onClickCancel = { viewModelState.onClickCancel() },
                    onClickConfirm = {
                        viewModelState.onClickConfirm()
                        navController.navigate(BottomNavItem.Reservation.route)
                    }
                )
            }*/

}

// 비트맵 전환
fun Bitmap.asImageBitmap(): ImageBitmap {
    return this.asImageBitmap()
}


@Composable
fun ImagePicker(
//    foodViewModel: FoodViewModel,
    onImageSelected: (ByteArray) -> Unit,
    onClickCancel: () -> Unit,
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraPermission by remember { mutableStateOf(false) }

    if (!cameraPermission) {
        CameraPermissionRequester {
            cameraPermission = true
        }
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
//    val options = arrayOf("갤러리에서 선택", "카메라로 찍기")
//    AlertDialog.Builder(context)
//        .setTitle("이미지 선택")
//        .setItems(options) { _, which ->
//            when (which) {
//                0 -> {
//                    galleryLauncher.launch("image/*")
//                }
//
//                1 -> {
//                    val file = createImageFile(context)
//                    imageUri = getImageUri(context, file)
//                    cameraLauncher.launch(imageUri!!)
//                }
//            }
//        }
//        .show()

}

/*
fun createImageFile(context: Context): File {
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
*/


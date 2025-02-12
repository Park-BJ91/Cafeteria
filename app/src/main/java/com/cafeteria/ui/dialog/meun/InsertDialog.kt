package com.cafeteria.ui.dialog.meun

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import java.io.File


@Composable
fun MenuInsertDialogImage(
    title: String,
    description: String,
    onClickCancel: () -> Unit,
    onClickGallery: (ByteArray) -> Unit,
    onClickCamara: (ByteArray) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 갤러리에서 이미지 선택
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                val inputStream = context.contentResolver.openInputStream(it)
                val imageBytes = inputStream?.readBytes()
                if (imageBytes != null) {
                    onClickGallery(imageBytes)
                }
            }
            onClickCancel()
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
                    onClickCamara(imageBytes)
                }
            } else {
                onClickCancel()
            }
        }
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Dialog(
            onDismissRequest = {
                onClickCancel()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
            )
            {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .wrapContentHeight()
                        .background(
                            color = Color.White,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color.LightGray)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min) // Row의 높이를 내부 컴포넌트에 맞춤
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            shape = RectangleShape,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // 버튼 배경색상
                                contentColor = Color.Gray, // 버튼 텍스트 색상
                            ),

                            ) {
                            Text(
                                text = "사진첩",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(color = Color.LightGray)
                        )

                        Button(
                            onClick = {
                                val file = createImageFile(context)
                                imageUri = getImageUri(context, file)
                                cameraLauncher.launch(imageUri!!)
                            },
                            shape = RectangleShape,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Blue, // 버튼 텍스트 색상
                            ),
                        ) {
                            Text(
                                text = "카메라",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}

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



// 기존 다이얼 로그
@Composable
fun MenuInsertDialog(
    title: String,
    description: String,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Dialog(
            onDismissRequest = {
                onClickCancel()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
            )
            {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .wrapContentHeight()
                        .background(
                            color = Color.White,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color.LightGray)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min) // Row의 높이를 내부 컴포넌트에 맞춤
                    ) {
                        Button(
                            onClick = { onClickCancel() },
                            shape = RectangleShape,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // 버튼 배경색상
                                contentColor = Color.Black, // 버튼 텍스트 색상
                                disabledContainerColor = Color.Gray, // 버튼 비활성화 배경 색상
                                disabledContentColor = Color.White, // 버튼 비활성화 텍스트 색상
                            ),

                            ) {
                            Text(
                                text = "취소",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(color = Color.LightGray)
                        )

                        Button(
                            onClick = { onClickConfirm() },
                            shape = RectangleShape,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // 버튼 배경색상
                                contentColor = Color.Red, // 버튼 텍스트 색상
                                disabledContainerColor = Color.Gray, // 버튼 비활성화 배경 색상
                                disabledContentColor = Color.White, // 버튼 비활성화 텍스트 색상
                            ),
                        ) {
                            Text(
                                text = "삭제",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}
package com.cafeteria.ui.menu

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafeteria.R
import com.cafeteria.data.dialog.CustomDialogImageData
import com.cafeteria.data.dialog.MenuInsertDatePickerData
import com.cafeteria.data.menu.MenuUiStateData
import com.cafeteria.destination.InterfaceNavDestination
import com.cafeteria.dto.Menu
import com.cafeteria.ui.dialog.meun.CustomDatePicker
import com.cafeteria.ui.dialog.meun.MenuInsertDialogImage
import com.cafeteria.ui.menu.MenuInsertDestination.titleRes
import com.cafeteria.ui.navigation.top.MenuTopAppBar
import com.cafeteria.viewmodel.datepicker.MenuDatePickerViewModel
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MenuInsertDestination : InterfaceNavDestination {
    override val route: String = R.string.menu_route_insert.toString()
    override val titleRes: Int = R.string.menu_title_insert
    const val param: String = "division"
    val routeArgs: String = "$route/{$param}"

}


@Composable
fun MenuInsertScreen(
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory),
    menuDatePickerViewModel: MenuDatePickerViewModel = viewModel(),
    backOnClick: () -> Unit,
    division: Int = 0
) {

    // nav는 상위로 올려서 둘을 통합시켜 이동 처리!!!
    val context = LocalContext.current

    // 전체 메뉴 정보
    val menuState by menuViewModel.menuState.collectAsState()

    // 메뉴 입력
    val menuName by menuViewModel.menuNames.collectAsState()

    // 카메라 앨범, 촬영
    val pictureState by menuViewModel.pictureState.collectAsState()

    // UI State
    val dateState by menuDatePickerViewModel.menuDatePickerState.collectAsState()
    val menuUiState by menuViewModel.uiState.collectAsState()


    // 수정 데이터 업로드 상태
    val menuEditState by menuViewModel.uiEditState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    if (division != 0) {
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                menuViewModel.menuDetailSearch(division)
            }
        }
    }

    MenuResultToast(
        menuUiState = menuUiState,
        menuViewModel = menuViewModel,
        backOnClick = backOnClick,
    )

    Scaffold(
        modifier = Modifier
            .padding(),
        topBar = {
            MenuTopAppBar(
                title = if (division == 0) stringResource(titleRes) else stringResource(R.string.menu_title_edit),
                back = true,
                backOnClick = backOnClick
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                /**
                 * 이미지 다이얼 로그
                 * */
                item {
                    MenuImageScreen(
                        menuUiState = menuUiState,
                        menuState = menuState,
                        pictureState = pictureState,
                        menuViewModel = menuViewModel,
                        division = division
                    )
                }
                /**
                 * 메뉴 입력
                 * */

                item {
                    MenuTextField(
                        nameValue = menuName.main,
                        nameLabel = "메인",
                        menuViewModel = menuViewModel
                    )
                }
                item {
                    MenuTextField(
                        nameValue = menuName.soup,
                        nameLabel = "국",
                        menuViewModel = menuViewModel
                    )
                }
                item {
                    MenuTextField(
                        nameValue = menuName.sideDish1,
                        nameLabel = "반찬1",
                        menuViewModel = menuViewModel
                    )
                }
                item {
                    MenuTextField(
                        nameValue = menuName.sideDish2,
                        nameLabel = "반찬2",
                        menuViewModel = menuViewModel
                    )
                }
                item {
                    MenuTextField(
                        nameValue = menuName.sideDish3,
                        nameLabel = "반찬3",
                        menuViewModel = menuViewModel
                    )
                }
                item {
                    MenuTextField(
                        nameValue = menuName.sideDish4,
                        nameLabel = "반찬4",
                        menuViewModel = menuViewModel
                    )
                }


                /**
                 * 메뉴 설명
                 * */
                item {
                    OutlinedTextField(
                        value = menuState.description,
                        onValueChange = { menuViewModel.menuDescriptionUpdate(it) },
                        label = { Text("설명") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    )
                }


                /**
                 * 선택 라디오 버튼
                 * */
                item {
                    MenuType(menuViewModel, menuState.mealTime)
                }

                /**
                 * 등록 날짜 다이얼 로그
                 * */
                item {
                    MenuInsertDatePicker(
                        menuDatePickerViewModel,
                        menuViewModel,
                        dateState,
                        menuEditState,
                        menuState.menuDate,
                    )
                }

                /**
                 * 서버에 등록
                 * */
                item {
                    Button(
                        modifier = Modifier.padding(top = 40.dp),
                        onClick = {
                            if (menuViewModel.menuInsertCheck()) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    if (!menuEditState) menuViewModel.menuInsert(0) else menuViewModel.menuInsert(menuState.id)

                                }

                            } else {
                                menuViewModel.insertValidationResult(true)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )

                    ) {
                        Text(text = if (!menuEditState) "등록" else "수정")
                    }
                }
            }

            // 입력 내용 확인
            if (menuUiState.validation) {
                Toast.makeText(context, "입력을 확인해 주세요", Toast.LENGTH_SHORT).show()
                menuViewModel.insertValidationResult(false)
            }

        }
    }

}


@Composable
fun MenuTextField(
    nameValue: String,
    nameLabel: String,
    menuViewModel: MenuViewModel,
) {

    OutlinedTextField(
        value = nameValue,
        onValueChange = { menuViewModel.menuNameUpdate(nameLabel, it) },
        label = { Text(nameLabel) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun MenuImageScreen(
    menuUiState: MenuUiStateData,
    menuState: Menu,
    pictureState: CustomDialogImageData,
    menuViewModel: MenuViewModel,
    division: Int,
) {
    if (menuUiState.pictureDialogState) {
        MenuInsertDialogImage(
            title = pictureState.title,
            description = pictureState.description,
            onClickCancel = pictureState.onClickCancel,
            onClickGallery = pictureState.onClickGallery,
            onClickCamara = pictureState.onClickCamara
        )
    }

    Button(
        onClick = { menuViewModel.showDialogOneImage() },
        modifier = Modifier.padding(bottom = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Text(text = "사진 업로드")
    }

    Box(
        modifier = Modifier
            .size(width = 380.dp, height = 200.dp)
    ) {
        OutlinedCard(
            modifier = Modifier
                .matchParentSize()
        ) {

            menuUiState.pictureImgData?.let { data ->
                val imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = "Loaded Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } ?: if (division > 0 && menuState.img != "") {
                val img = base64ToByte(menuState.img)
//                                menuViewModel.menuEditImgLoad(img)
                val bitMap = BitmapFactory.decodeByteArray(img, 0, img.size)
                Image(
                    bitmap = bitMap.asImageBitmap(),
                    contentDescription = "Loaded Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.empty),
                    contentDescription = "Empty Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }

}

// 타입 라디오 박스
@Composable
fun MenuType(
    menuViewModel: MenuViewModel,
    mealTime: String,
) {
    val options = listOf("중식", "석식")
    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(mealTime) }

    Row(
        Modifier.padding(top = 8.dp)
    ) {
        options.forEach { option ->
            Row(
                Modifier
                    .wrapContentSize()
                    .height(56.dp)
                    .selectable(
                        selected = option == selectedOption,
                        onClick = {
                            onOptionSelected(option)
                            menuViewModel.menuType(option)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = null
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

}

// 날짜 선택 Dialog
@Composable
fun MenuInsertDatePicker(
    menuDatePickerViewModel: MenuDatePickerViewModel,
    menuViewModel: MenuViewModel,
    dateState: MenuInsertDatePickerData,
    menuEditState: Boolean,
    menuDate: String,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = if (!menuEditState) dateState.onClickConfirm ?: "날짜를 선택해 주세요" else menuDate,
            onValueChange = {},
            label = {
                Text("등록할 날짜")
            },
            enabled = false,
            trailingIcon = {
                IconButton(
                    onClick = { menuDatePickerViewModel.showMenuDatePrickerDialog(true) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.Black,
                disabledTrailingIconColor = Color.Black,
                disabledBorderColor = Color.Black
            ),
            modifier = Modifier
                .clickable(
                    onClick = {
                        menuDatePickerViewModel.showMenuDatePrickerDialog(true)
                    }
                )
        )
    }

    // 날짜 선택 Dialog
    if (dateState.isShowDialog) {
        CustomDatePicker(
            menuDatePickerViewModel = menuDatePickerViewModel,
            menuViewModel = menuViewModel,
            selectedDate = dateState.selectedDate,
//            onClickCancel = state.onClickCancel,
//            onClickConfirm = state.onClickConfirm
        )
    }

}

private fun base64ToByte(base64Data: String): ByteArray {
    return Base64.decode(base64Data, Base64.DEFAULT)

}



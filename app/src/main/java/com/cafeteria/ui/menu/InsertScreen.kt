package com.cafeteria.ui.menu

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cafeteria.R
import com.cafeteria.data.dialog.MenuInsertDatePickerData
import com.cafeteria.destination.InterfaceNavDestination
import com.cafeteria.destination.bottom.BottomNavItem
import com.cafeteria.dto.MenuName
import com.cafeteria.ui.dialog.meun.CustomDatePicker
import com.cafeteria.ui.dialog.meun.MenuInsertDialogImage
import com.cafeteria.ui.navigation.top.MenuTopAppBar
import com.cafeteria.viewmodel.datepicker.MenuDatePickerViewModel
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MenuEntryDestination : InterfaceNavDestination {
    override val route: String = R.string.route_menu_entry.toString()
    override val titleRes: Int = R.string.title_menu_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuEntryScreen(
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory),
    menuDatePickerViewModel: MenuDatePickerViewModel = viewModel(),
    backOnClick: () -> Unit
) {

    // nav는 상위로 올려서 둘을 통합시켜 이동 처리!!!

    val context = LocalContext.current
    // 메뉴 입력
    val menuCount = 6
    val menuList = remember { List(menuCount) { mutableStateOf("") } }
    // 메뉴 설명
    var descriptionValue by remember { mutableStateOf("") }
    // 카메라 앨범, 촬영
    val pictureState by menuViewModel.pictureState.collectAsState()
    // UI State
    val dateState = menuDatePickerViewModel.menuDatePickerState.collectAsState()
    val menuUiSate = menuViewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    // 등록 결과
    when (menuUiSate.value.insertResult) {
        "true" -> {
            Toast.makeText(context, "등록 완료.", Toast.LENGTH_SHORT).show()
            backOnClick()
        }

        "false" -> {
            Toast.makeText(context, "등록 실패", Toast.LENGTH_SHORT).show()
            menuViewModel.insertResultReset()
        }

        "duplicate" -> {
            Toast.makeText(context, "등록 목록을 확인 해보세요.", Toast.LENGTH_SHORT).show()
            menuViewModel.insertResultReset()
        }
    }


    Scaffold(
        modifier = Modifier
            .padding(),
        topBar = {
            MenuTopAppBar(
                title = stringResource(R.string.title_menu_entry),
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
                /**
                 * 이미지 다이얼 로그
                 * */
                item {
                    if (menuUiSate.value.pictureDialogState) {
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
                            menuUiSate.value.pictureImgData?.let { data ->
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
                            }
                        }
                    }
                }

                /**
                 * 메뉴 입력
                 * */

                /**
                 * 메뉴 입력
                 * */
                items(menuList.size) { index ->
                    val menuState = menuList[index]
                    OutlinedTextField(
                        value = menuState.value,
                        onValueChange = { menuState.value = it },
                        label = { Text(menuKey(index)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                /**
                 * 메뉴 설명
                 * */


                /**
                 * 메뉴 설명
                 * */
                item {
                    OutlinedTextField(
                        value = descriptionValue,
                        onValueChange = {
                            descriptionValue = it
                        },
                        label = {
                            Text("설명")
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    )
                }

                /**
                 * 선택 라디오 버튼
                 * */

                /**
                 * 선택 라디오 버튼
                 * */
                item {
                    MenuType(menuViewModel)
                }

                /**
                 * 등록 날짜 다이얼 로그
                 * */

                /**
                 * 등록 날짜 다이얼 로그
                 * */
                item {
                    MenuInsertDatePicker(
                        menuDatePickerViewModel,
                        menuViewModel,
                        dateState.value
                    )
                }

                /**
                 * 서버에 등록
                 * */

                /**
                 * 서버에 등록
                 * */
                item {
                    Button(
                        modifier = Modifier.padding(top = 40.dp),
                        onClick = {
                            val menuJson = menuSet(menuList)
                            if (menuViewModel.foodInsertCheck(menuJson, descriptionValue)) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    menuViewModel.foodInsert()
                                }
//                                delay(1000)
//                                navController.navigate(BottomNavItem.Insert.route)
                                // 등록 목록을 만들고 이동

                            } else {
                                menuViewModel.insertValidationResult(true)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )

                    ) {
                        Text("등록")
                    }
                }
            }

            if (menuUiSate.value.insertValidation) {
                Toast.makeText(context, "입력을 확인해 주세요", Toast.LENGTH_SHORT).show()
                menuViewModel.insertValidationResult(false)
            }

        }
    }

}

// 메뉴 label
fun menuKey(index: Int): String {
    return when (index) {
        0 -> "메인"
        1 -> "국"
        2 -> "반찬1"
        3 -> "반찬2"
        4 -> "반찬3"
        5 -> "반찬4"
        6 -> "반찬5"
        else -> "메뉴"
    }
}

fun menuSet(menuList: List<MutableState<String>>): MenuName {
    return MenuName(
        main = menuList[0].value,
        soup = menuList[1].value,
        sideDish1 = menuList[2].value,
        sideDish2 = menuList[3].value,
        sideDish3 = menuList[4].value,
        sideDish4 = menuList[5].value,
    )
}

// 타입 라디오 박스
@Composable
fun MenuType(
    menuViewModel: MenuViewModel
) {
    val options = listOf("중식", "석식")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

    LaunchedEffect(Unit) {
        menuViewModel.foodType(options[0])
    }

    Row(
        Modifier.padding(top = 8.dp)
    ) {
        options.forEach { option ->
            Row(
                Modifier
                    .wrapContentSize()
                    .height(56.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionSelected(option)
                            menuViewModel.foodType(option)
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
    dateState: MenuInsertDatePickerData
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = dateState.onClickConfirm ?: "날짜를 선택해 주세요",
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

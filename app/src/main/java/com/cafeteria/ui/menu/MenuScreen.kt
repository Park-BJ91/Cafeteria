package com.cafeteria.ui.menu

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafeteria.R
import com.cafeteria.dto.Menu
import com.cafeteria.ui.LoadingIndicator
import com.cafeteria.ui.navigation.top.MenuTopAppBar
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


// 네비 경로는 바텀 네비에 있어서 설정 X
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory),
    paddingValues: PaddingValues,
    menuAddOnClick: (Int) -> Unit,
    menuDetailOnClick: (Int) -> Unit,
    menuEditOnClick: (Int) -> Unit,
    menuDeleteOnClick: () -> Unit,
) {

    val tag = "menu"
    val menuListState by menuViewModel.menuListState.collectAsState()
    val menuUiState by menuViewModel.uiState.collectAsState()
//    val topBarScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val coroutineScope = rememberCoroutineScope()

//    val currentDate = LocalDate.now()
//    val month = currentDate.monthValue

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.Main) {
//            menuViewModel.menuSearch(month)
            menuViewModel.menuSearch()
        }
    }

    MenuResultToast(
        menuUiState = menuUiState,
        menuViewModel = menuViewModel,
        backOnClick = menuDeleteOnClick,
    )


    Scaffold(
        modifier = Modifier
//            .nestedScroll(topBarScroll.nestedScrollConnection)
            .padding(),
        topBar = {
            MenuTopAppBar(
                title = stringResource(R.string.menu_title),
                back = false,
//                topBarScroll = topBarScroll,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { menuAddOnClick(0) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(
                        end = WindowInsets.safeDrawing
                            .asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "ADD Menu"
                )
            }
        }
    ) {
        if (menuUiState.loading == "false") {
            LoadingIndicator()
        } else {

            // 아이콘 생성 후 MenuColumnList 전환

            if (menuUiState.loading == "true" && menuListState.menuList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("데이터가 존재 하지 않습니다.")
                }
            } else if (menuUiState.loading == "error") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("서버에 요청이 실패 했습니다")
                }
            } else {
                // 이미지 + 텍스트 형식
                MenuColumnList(
                    menuViewModel = menuViewModel,
                    menuList = menuListState.menuList,
                    menuDetailOnClick = menuDetailOnClick,
                    paddingValues = it,
                    menuEditOnClick = menuEditOnClick,
                )
                // 텍스트 형식 으로 보여 줄지 MenuColumnTextList()
            }


        }

    }

}

@Composable
fun MenuColumnList(
    menuViewModel: MenuViewModel,
    menuList: List<Menu>,
    menuDetailOnClick: (Int) -> Unit,
    menuEditOnClick: (Int) -> Unit,
    paddingValues: PaddingValues,
) {
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
//            .fillMaxSize()
            .padding(paddingValues)
    ) {

        items(items = menuList) { menu ->

            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .height(120.dp)
                    .clickable(
                        onClick = { menuDetailOnClick(menu.id) },
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 5.dp
                )
            ) {

                Row {
                    Box(
                        modifier = Modifier.weight(0.4f)
                    ) {
                        if (menu.img != "") {
                            val img = base64ToByte(menu.img)
                            val imageBitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
                            Image(
                                bitmap = imageBitmap.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .fillMaxWidth()
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.empty),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .fillMaxWidth()
                            )
                        }

                    }

                    Row(modifier = Modifier.weight(0.05f)) { }
                    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.DarkGray)

                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillParentMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = menu.menuDate,
                            style = TextStyle(brush = Brush.linearGradient(colors = gradientColors))
                        )
                        Text(
                            text = menu.mealTime,
                            style = TextStyle(brush = Brush.linearGradient(colors = gradientColors))
                        )
                        Text(
                            text = menu.description,
                            style = TextStyle(brush = Brush.linearGradient(colors = gradientColors))
                        )

                    }

                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillParentMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { menuEditOnClick(menu.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    menuViewModel.menuDelete(menu.id)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                            )
                        }
                    }

                }


            }
        }

        item {
            Box(Modifier.height(300.dp))
        }


    }


}

// 이미지 Base64 -> ByteArray
private fun base64ToByte(base64Data: String): ByteArray {
    return Base64.decode(base64Data, Base64.DEFAULT)

}

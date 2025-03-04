package com.cafeteria.ui.home


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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafeteria.R
import com.cafeteria.data.dialog.MyCalendar
import com.cafeteria.dto.Menu
import com.cafeteria.dto.isValid
import com.cafeteria.ui.LoadingIndicator
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val tag = "home"


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val menuListState by menuViewModel.menuListState.collectAsState()
    val menuUiState by menuViewModel.uiState.collectAsState()

    if (menuUiState.loading == "false") {
        LoadingIndicator()
    }


    /**
     *  Row값 기반으로 ViewModel 업데이트 :: 업데이트 내역은 음식 리스트
     * */

    val calendar = MyCalendar
    val result = calendar.findAllCalendar()

    val calendarMap = result["resultMap"] as HashMap<*, *>
    val toDay = result["toDay"] as Int
    val currentTotalDay = result["dayInMonth"] as Int

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = "${result["yearMonth"]}",
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 24.dp)
            )
            Box {
                CalendarRow(menuViewModel, calendarMap, toDay, currentTotalDay)
            }
            when (menuUiState.loading) {
                "true" -> HomeMenuScreen(menuListState.menuList, toDay)
                "fail" -> Text("서버 연결에 실패 했습니다.")
                "empty" -> Text("해당 날짜에 메뉴가 존재 하지 않습니다.")
            }
        }
    }
}

@Composable
fun CalendarRow(
    menuViewModel: MenuViewModel,
    calendarMap: HashMap<*, *>,
    toDay: Int,
    currentTotalDay: Int,
) {

    val rowState = rememberLazyListState()
    var counter by remember { mutableIntStateOf(toDay - 1) }
    val coroutineScope = rememberCoroutineScope()

    /*
    * option 에 년월 추가 선택된 option +
    * click 한 날을 전달
    * (후에 추가)
    * */

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        state = rowState
    ) {
        items(currentTotalDay) {
            val isToday = remember {
                derivedStateOf {
                    counter == it
                }
            }
            ElevatedCard(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isToday.value) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
//                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable(
                        onClick = {
                            coroutineScope.launch {
                                menuViewModel.menuDetailTarget(calendarMap["selected$it"].toString())
                                rowState.scrollToItem(it)
                                counter = it
                            }
                        }
                    ),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("${it + 1} ")
                    Text("${calendarMap["week$it"]}")
                }
            }

        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            menuViewModel.menuDetailTarget(calendarMap["selected${toDay - 1}"].toString())
            rowState.scrollToItem(toDay - 1)
        }
    }
}


@Composable
fun HomeMenuScreen(
    menuListState: List<Menu>,
    toDay: Int
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(menuListState) {
            MainMenuBody(menu = it)
        }
        /*
                item {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "중식",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                        ) {

                            ElevatedCard(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                elevation = CardDefaults.elevatedCardElevation(
                                    defaultElevation = 6.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {

                                if (!menuData.menuName.isValid()) {
                                    Image(
                                        painter = painterResource(R.drawable.mix),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "비빔",
                                        modifier = Modifier.fillMaxSize()
                                    )

                                } else if (menuData.img != null) {
        //                            val imageBitmap = BitmapFactory.decodeByteArray(menuData.img, 0, menuData.img.size)
        //                            Image(
        //                                bitmap = imageBitmap.asImageBitmap(),
        //                                contentScale = ContentScale.Crop,
        //                                contentDescription = menuData.description,
        //                                modifier = Modifier.fillMaxSize()
        //                            )

                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (menuData.img == null) "메인 메뉴 비빔밥" else menuData.description,
                                        modifier = Modifier.padding(start = 15.dp)

                                    )
                                    FilledTonalButton(
                                        onClick = {},
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text("예약")
                                    }
                                }

                            }
                        }
                    }
                }
        */

        /*
                item {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {
                        Text(
                            text = "석식",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                        ) {

                            ElevatedCard(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                elevation = CardDefaults.elevatedCardElevation(
                                    defaultElevation = 6.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.nodle),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "짜장",
                                    modifier = Modifier.fillMaxSize()
                                )

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "메인 메뉴 짜장",
                                        modifier = Modifier.padding(start = 15.dp)

                                    )
                                    FilledTonalButton(
                                        onClick = {},
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text("예약")
                                    }
                                }
                            }
                        }
                    }
                }
        */

    }
}

@Composable
fun MainMenuBody(
    menu: Menu
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = menu.mealTime,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(10.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                if (menu.img == "") {
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentScale = ContentScale.Crop,
                        contentDescription = menu.menuName.main,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    val img = base64ToByte(menu.img)
                    val imageBitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = menu.menuName.main,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = menu.description,
                        modifier = Modifier.padding(start = 15.dp)

                    )
                    FilledTonalButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("예약")
                    }
                }

            }
        }
    }
}

// 이미지 Base64 -> ByteArray
private fun base64ToByte(base64Data: String): ByteArray {
    return Base64.decode(base64Data, Base64.DEFAULT)

}

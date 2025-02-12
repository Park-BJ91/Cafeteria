package com.cafeteria.ui.home



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cafeteria.CameraPermissionRequester
import com.cafeteria.R
import com.cafeteria.data.calendar.MyCalendar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    /**
     *  Row값 기반으로 ViewModel 업데이트 :: 업데이트 내역은 음식 리스트
     * */

    val calendar = MyCalendar
    val result = calendar.findAllCalendar()

    val calendarMap = result["resultMap"] as HashMap<*, *>
    val toDay = result["toDay"] as Int

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
        ) {
            Text(
                text = "${result["yearMonth"]}",
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 24.dp)
            )

            Box {
                CalendarRow(calendarMap, toDay)
            }
            FoodMenu()

        }

    }

}

@Composable
fun CalendarRow(
    calendarMap: HashMap<*, *>,
    toDay: Int
) {

    val rowState = rememberLazyListState()
    var counter by remember { mutableIntStateOf(toDay - 1) }
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        state = rowState
    ) {
        items(calendarMap.size / 2) {
            val isToday = remember {
                derivedStateOf {
                    counter == it
                }
            }
            ElevatedCard (
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if(isToday.value) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
//                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable(
                        onClick = {
                            coroutineScope.launch {
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
            rowState.scrollToItem(toDay - 1)
        }
    }
}



@Composable
fun FoodMenu(

) {


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        Image(
                            painter = painterResource(R.drawable.mix),
                            contentScale = ContentScale.Crop,
                            contentDescription = "비빔",
                            modifier = Modifier.fillMaxSize()
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "메인 메뉴 비빔밥",
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

        item {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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

    }
}
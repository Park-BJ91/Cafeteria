package com.cafeteria.ui.dialog.meun

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cafeteria.viewmodel.datepicker.MenuDatePickerViewModel
import com.cafeteria.viewmodel.menu.MenuViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    menuDatePickerViewModel: MenuDatePickerViewModel,
    menuViewModel: MenuViewModel,
    selectedDate: String?,
//    onClickCancel: () -> Unit,
//    onClickConfirm: () -> Unit,
) {

    val toDay = LocalDateTime.now().year

    val datePickerState = rememberDatePickerState(
        yearRange = toDay..2060,
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = selectedDate?.toLong() ?: System.currentTimeMillis(), // selectedDate가 null인 경우 현재 시간을 기본값으로 사용,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // 날짜 제한 조건
                return true
            }
        })

    DatePickerDialog(
//        onDismissRequest = { onClickCancel() },
        onDismissRequest = { menuDatePickerViewModel.showMenuDatePrickerDialog(false) },
        dismissButton = {
            Button(
                onClick = { menuDatePickerViewModel.showMenuDatePrickerDialog(false) }
            ) {
                Text("취소")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                        val yyyyMMdd = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(Date(selectedDateMillis))
//                        onClickConfirm(yyyyMMdd)
                        menuDatePickerViewModel.menuDateUpdate(yyyyMMdd, selectedDateMillis)
                        menuViewModel.foodDateUpdate(yyyyMMdd)
                    }
                }
            ) {
                Text("설정")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
/*        val datePickerState = rememberDatePickerState(
            yearRange = 2024..2030,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate?.let {
                val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).apply {
                    // initialSelectedDateMillis는 UTC 시간을 받고 있다.
                    // 아래처럼 timeZone을 추가해서 UTC 시간으로 설정해야한다.
                    // 한국에서 실행한다면 -9시간을 적용하기 때문이다.
                    // 만약에 아래 코드가 없다면 20240228을 넘겼을 때, 안드로이드는 KST 20240228000000 으로 인식할 것이고,
                    // 이를 UTC 시간으로 변환하면서 -9시간을 적용하기 때문에 결과적으로 20240228을 넘기면 2024년 2월 27일에 선택이 되있는 문제가 발생한다.
                    timeZone = TimeZone.getTimeZone("UTC")
                }

                formatter.parse(it)?.time
                    ?: System.currentTimeMillis() // 날짜 파싱 실패 시 현재 시간을 기본값으로 사용
            } ?: System.currentTimeMillis(), // selectedDate가 null인 경우 현재 시간을 기본값으로 사용,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    // 날짜 제한 조건
                    return true

                }
            })*/
        DatePicker(state = datePickerState)

        /*
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(onClick = {
                        onClickCancel()
                    }) {
                        Text(text = "취소")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                            val yyyyMMdd = SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                            ).format(Date(selectedDateMillis))

                            onClickConfirm(yyyyMMdd)
                        }
                    }) {
                        Text(text = "확인")
                    }
                }
        */


    }

}
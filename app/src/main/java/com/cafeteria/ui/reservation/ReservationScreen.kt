package com.cafeteria.ui.reservation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ReservationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "예약 정보",
            modifier = Modifier.align(Alignment.Center)
        )
    }


}
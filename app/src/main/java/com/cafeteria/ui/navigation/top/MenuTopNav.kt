package com.cafeteria.ui.navigation.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopAppBar(
    title: String,
    back: Boolean,
//    topBarScroll: TopAppBarScrollBehavior? = null,
    backOnClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(title)
            }
        },
//        scrollBehavior = topBarScroll,
        navigationIcon = {
            if (back) {
                IconButton(onClick = backOnClick) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "back"
                    )
                }
            }
        },
    )
}
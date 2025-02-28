package com.cafeteria.ui.menu

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafeteria.R
import com.cafeteria.destination.InterfaceNavDestination
import com.cafeteria.viewmodel.menu.MenuViewModel
import com.cafeteria.viewmodel.provider.AppViewModelProvider
import kotlinx.coroutines.launch


object MenuDetailDestination : InterfaceNavDestination {
    override val route: String = R.string.menu_route_detail.toString()
    override val titleRes: Int = R.string.menu_title_detail
    const val params = "menuId"
    val routeArgs = "$route/{$params}"
}


@Composable
fun MenuDetailScreen(
    menuViewModel: MenuViewModel = viewModel(factory = AppViewModelProvider.Factory),
    menuId: Int,
    onClickEdit: (Int) -> Unit,
    onClickDelete: () -> Unit,
) {

    val menuState by menuViewModel.menuState.collectAsState()
    val menuUiState = menuViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        menuViewModel.menuDetailSearch(menuId)
    }

    MenuResultToast(
        menuUiState = menuUiState.value,
        menuViewModel = menuViewModel,
//        context = context,
        backOnClick = onClickDelete,
    )

    Card(
        modifier = Modifier
            .padding(top = 40.dp)
    ) {
        if (menuState.img != "") {
            val img = base64ToByte(menuState.img)
            val imageBitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth()
            )
        } else {
            Image(
                painter = painterResource(R.drawable.empty),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth()
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(menuState.menuDate)
            Text(menuState.mealTime)
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(menuState.description)
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(menuState.menuName.main)
            Text(menuState.menuName.soup)
            Text(menuState.menuName.sideDish1)
            Text(menuState.menuName.sideDish2)
            Text(menuState.menuName.sideDish3)
            Text(menuState.menuName.sideDish4)
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            IconButton(
                onClick = { onClickEdit(menuId) }
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "수정")
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        menuViewModel.menuDelete(menuId)
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "삭제")
            }
        }
    }
}


private fun base64ToByte(base64Data: String): ByteArray {
    return Base64.decode(base64Data, Base64.DEFAULT)

}


package com.cafeteria.viewmodel.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class BottomNavViewModel : ViewModel() {

    private val _bottomNavSelected = MutableStateFlow(1)
    val bottomNavSelected = _bottomNavSelected

}

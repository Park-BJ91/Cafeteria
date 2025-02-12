package com.cafeteria.viewmodel.provider


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cafeteria.MainApplication
import com.cafeteria.viewmodel.food.FoodViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FoodViewModel(
                cafeteriaApplication().container.foodRepository
            )
        }
    }
}

fun CreationExtras.cafeteriaApplication(): MainApplication {
    return (this[AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
}
package com.cafeteria

import android.app.Application
import com.cafeteria.config.AppContainer
import com.cafeteria.config.AppDataContainer

class MainApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}
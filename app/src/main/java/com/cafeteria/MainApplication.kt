package com.cafeteria

import android.app.Application
import android.util.Log
import com.cafeteria.config.AppContainer
import com.cafeteria.config.AppDataContainer

class MainApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        Log.d("xxx","#$#$$$")
        super.onCreate()
        container = AppDataContainer(this)
    }

}
package com.demo.cafeteria

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CafeteriaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
package com.example.uksivtcompanion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UksivtApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
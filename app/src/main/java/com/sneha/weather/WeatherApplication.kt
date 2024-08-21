package com.sneha.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Sneha on 15-08-2024.
 */
@HiltAndroidApp
class WeatherApplication : Application() {
    companion object {
        var instance: WeatherApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
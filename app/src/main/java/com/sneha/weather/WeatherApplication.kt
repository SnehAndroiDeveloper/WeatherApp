package com.sneha.weather

import android.app.Application

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherApplication : Application() {
    companion object {
        var instance: WeatherApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
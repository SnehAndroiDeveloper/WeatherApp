package com.sneha.weather.data.datasource.network.controllers

import com.sneha.network.NetworkManager
import com.sneha.weather.WeatherApplication

/**
 * Created by Sneha on 15-08-2024.
 */
open class BaseNetworkController(baseURL : String) {
    protected val networkManager by lazy { NetworkManager(baseURL) }

    fun isInternetAvailable() = WeatherApplication.instance?.applicationContext?.let {
        networkManager.isInternetAvailable(
            it
        )
    }
}
package com.sneha.weather.data.datasource.local_db.controllers

import com.sneha.local_db.interactions.WeatherDBInteraction
import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.WeatherApplication
import com.sneha.weather.data.datasource.network.models.response.weather_info.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherDBController : CoroutineScope {
    private val interaction by lazy {
        WeatherApplication.instance?.applicationContext?.let {
            WeatherDBInteraction(
                it
            )
        }
    }

    suspend fun insertWeatherEntity(weatherEntity: WeatherEntity) {
        withContext(coroutineContext) {
            interaction?.insertWeatherEntity(weatherEntity)
        }
    }

    suspend fun getWeatherEntity(latitude: String, longitude: String) =
        withContext(coroutineContext) { interaction?.getWeatherEntity(latitude, longitude) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
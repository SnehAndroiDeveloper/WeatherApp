package com.sneha.weather.data.datasource.local_db.controllers

import android.content.Context
import com.sneha.local_db.interactions.WeatherDBInteraction
import com.sneha.local_db.models.WeatherEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherDBController @Inject constructor(@ApplicationContext context: Context) :
    CoroutineScope {
    private val interaction by lazy {
        WeatherDBInteraction(
            context
        )
    }

    suspend fun insertWeatherEntity(weatherEntity: WeatherEntity) {
        withContext(coroutineContext) {
            interaction.insertWeatherEntity(weatherEntity)
        }
    }

    suspend fun getWeatherEntity(latitude: String, longitude: String) =
        withContext(coroutineContext) { interaction.getWeatherEntity(latitude, longitude) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
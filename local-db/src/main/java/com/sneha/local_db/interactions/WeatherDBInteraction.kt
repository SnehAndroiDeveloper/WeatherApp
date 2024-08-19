package com.sneha.local_db.interactions

import android.content.Context
import com.sneha.local_db.models.WeatherEntity

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherDBInteraction(private val context: Context) : RoomDatabaseInteraction() {

    suspend fun insertWeatherEntity(weatherEntity: WeatherEntity) {
        getDBInstance(context)?.weatherEntityDao()?.insertWeatherEntity(weatherEntity)
    }

    suspend fun getWeatherEntity(latitude: String, longitude: String): WeatherEntity?  {
        return getDBInstance(context)?.weatherEntityDao()?.getWeatherEntity(latitude, longitude)
    }
}
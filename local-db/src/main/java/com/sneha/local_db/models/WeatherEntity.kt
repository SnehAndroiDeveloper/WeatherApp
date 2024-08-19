package com.sneha.local_db.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sneha on 15-08-2024.
 */
@Keep
@Entity(
    tableName = "weather",
    primaryKeys = ["latitude", "longitude"]
)
data class WeatherEntity(
    val id: Int = 0,
    val temperature: Int,
    val feelsLike: Int,
    val latitude: String,
    val longitude: String,
    val location: String,
    val region: String,
    val country: String,
    val localtime: String,
    val weatherDescription: String,
    val weatherIcon: String,
    val sunrise: String,
    val sunset: String,
    val humidity: Int,
    val uvIndex: Int,
    val windSpeed: Int,
    val pressure: Int,
    val isDay: Boolean
)

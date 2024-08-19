package com.sneha.weather.data.models

/**
 * Created by Sneha on 16-08-2024.
 */
data class WeatherInfoModel(
    val location: String = "",
    val region: String = "",
    val country: String = "",
    val isDay: Boolean = true,
    val weatherIconURL: String = "",
    val currentTemperature: Int = 0,
    val weatherDescription: String = "",
    val feelsLikeTemperature: Int = 0,
    val localTime: String = "",
    val sunRiseTime: String = "",
    val sunSetTime: String = "",
    val humidity: Int = 0,
    val uvIndex: Int = 0,
    val windSpeed: Int = 0,
    val pressure: Int = 0,
    val latitude: String = "",
    val longitude: String = ""
)

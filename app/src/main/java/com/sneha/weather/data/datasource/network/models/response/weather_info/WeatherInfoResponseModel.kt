package com.sneha.weather.data.datasource.network.models.response.weather_info

import androidx.annotation.Keep

@Keep
data class WeatherInfoResponseModel(
    val error: Error? = null,
    val success: Boolean? = false,
    val current: Current? = Current(),
    val location: Location? = Location(),
    val request: Request? = Request()
)
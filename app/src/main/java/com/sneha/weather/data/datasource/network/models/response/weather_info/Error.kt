package com.sneha.weather.data.datasource.network.models.response.weather_info

import androidx.annotation.Keep

@Keep
data class Error(
    val code: Int = 0,
    val info: String = "",
    val type: String = ""
)
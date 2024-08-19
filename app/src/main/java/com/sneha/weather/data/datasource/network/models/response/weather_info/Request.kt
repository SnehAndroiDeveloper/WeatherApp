package com.sneha.weather.data.datasource.network.models.response.weather_info

import androidx.annotation.Keep

@Keep
data class Request(
    val language: String = "",
    val query: String = "",
    val type: String = "",
    val unit: String = ""
)
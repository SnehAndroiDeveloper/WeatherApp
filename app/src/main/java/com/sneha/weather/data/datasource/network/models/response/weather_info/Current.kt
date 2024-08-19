package com.sneha.weather.data.datasource.network.models.response.weather_info

import androidx.annotation.Keep

@Keep
data class Current(
    val cloudcover: Int = 0,
    val feelslike: Int = 0,
    val humidity: Int = 0,
    val is_day: String = "",
    val observation_time: String = "",
    val precip: Int = 0,
    val pressure: Int = 0,
    val temperature: Int = 0,
    val uv_index: Int = 0,
    val visibility: Int = 0,
    val weather_code: Int = 0,
    val weather_descriptions: List<String> = listOf(),
    val weather_icons: List<String> = listOf(),
    val wind_degree: Int = 0,
    val wind_dir: String = "",
    val wind_speed: Int = 0
)
package com.sneha.weather.data.datasource.network.models.response.weather_info

import androidx.annotation.Keep

@Keep
data class Location(
    val country: String = "",
    val lat: String = "",
    val localtime: String = "",
    val localtime_epoch: Int = 0,
    val lon: String = "",
    val name: String = "",
    val region: String = "",
    val timezone_id: String = "",
    val utc_offset: String = ""
)
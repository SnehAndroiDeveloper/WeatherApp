package com.sneha.weather.data.datasource.network.models.response.sun_info

import androidx.annotation.Keep

@Keep
data class Results(
    val astronomical_twilight_begin: String = "",
    val astronomical_twilight_end: String = "",
    val civil_twilight_begin: String = "",
    val civil_twilight_end: String = "",
    val day_length: String = "",
    val nautical_twilight_begin: String = "",
    val nautical_twilight_end: String = "",
    val solar_noon: String = "",
    val sunrise: String = "",
    val sunset: String = ""
)
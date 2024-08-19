package com.sneha.weather.data.datasource.network.models.response.sun_info

import androidx.annotation.Keep

@Keep
data class SunInfoResponseModel(
    val results: Results = Results(),
    val status: String = "",
    val tzid: String = ""
)
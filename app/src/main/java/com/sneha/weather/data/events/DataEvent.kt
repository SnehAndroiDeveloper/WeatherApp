package com.sneha.weather.data.events

import com.sneha.weather.data.enums.DataStateEnum

/**
 * Created by Sneha on 15-08-2024.
 */
data class DataEvent<T>(
    val state: DataStateEnum,
    val data: Any? = null,
    val dataType: T,
    val message: String? = null,
    val code: Int = 0,
)

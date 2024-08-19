package com.sneha.weather.data.events

import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.enums.UiStateEnum

/**
 * Created by Sneha on 15-08-2024.
 */
data class UiEvent<T>(
    val state: UiStateEnum,
    val data: Any? = null,
    val dataType: T,
)

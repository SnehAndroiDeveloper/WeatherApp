package com.sneha.weather.data.enums

/**
 * Created by Sneha on 19-08-2024.
 */
enum class UserState(val value : Int) {
    NOT_SELECTED_LOCATION(0),
    LOADING_WEATHER_INFO(1),
    WEATHER_INFO_LOADED(2),
    ERROR_LOADING_WEATHER_INFO(3)
}
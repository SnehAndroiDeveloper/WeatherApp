package com.sneha.weather.events

/**
 * Created by Sneha on 15-08-2024.
 */
sealed class DashboardClickEvents {
    data object OnSearchClick : DashboardClickEvents()
}
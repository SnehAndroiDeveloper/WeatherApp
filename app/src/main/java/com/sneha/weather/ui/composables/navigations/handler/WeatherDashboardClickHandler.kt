package com.sneha.weather.ui.composables.navigations.handler

import androidx.navigation.NavHostController
import com.sneha.weather.events.DashboardClickEvents

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherDashboardClickHandler {

    internal fun onClick(
        event: DashboardClickEvents,
        navController: NavHostController
    ) {
        when (event) {
            DashboardClickEvents.OnSearchClick -> {
                navController.popBackStack()
            }
        }
    }
}
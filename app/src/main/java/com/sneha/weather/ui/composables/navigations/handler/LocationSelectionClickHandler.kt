package com.sneha.weather.ui.composables.navigations.handler

import androidx.navigation.NavHostController
import com.sneha.weather.events.LocationSelectionClickEvents
import com.sneha.weather.navigation.NavigationDestination

/**
 * Created by Sneha on 15-08-2024.
 */
class LocationSelectionClickHandler {

    internal fun onClick(
        event: LocationSelectionClickEvents,
        navController: NavHostController
    ) {
        when (event) {
            LocationSelectionClickEvents.OpenDashboard -> {
                navController.navigate(
                    NavigationDestination.WeatherDashboard.fullRoute
                ) {
                    popUpTo(NavigationDestination.LocationSelection.fullRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }
}
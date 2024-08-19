package com.sneha.weather.ui.composables.navigations.handler

import androidx.navigation.NavHostController
import com.sneha.weather.events.OnBoardClickEvents
import com.sneha.weather.navigation.NavigationDestination

/**
 * Created by Sneha on 15-08-2024.
 */
class OnBoardClickHandler {

    internal fun onClick(
        event: OnBoardClickEvents,
        navController: NavHostController
    ) {
        when (event) {
            OnBoardClickEvents.OpenDashboard -> {
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
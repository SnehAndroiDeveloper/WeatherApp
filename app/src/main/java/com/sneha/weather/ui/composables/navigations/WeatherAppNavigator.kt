package com.sneha.weather.ui.composables.navigations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sneha.weather.navigation.NavigationDestination
import com.sneha.weather.ui.composables.navigations.handler.LocationSelectionClickHandler
import com.sneha.weather.ui.composables.screens.dashboard.WeatherDashboardScreen
import com.sneha.weather.ui.composables.screens.location_selection.LocationSelectionScreen
import com.sneha.weather.viewmodels.DashboardViewModel
import com.sneha.weather.viewmodels.LocationSelectionViewModel

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun WeatherAppNavigator(navController: NavHostController, destination: NavigationDestination) {
    WeatherNavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(NavigationDestination.LocationSelection.fullRoute) {
            val viewModel = hiltViewModel<LocationSelectionViewModel>()
            LocationSelectionScreen(viewModel) { event ->
                LocationSelectionClickHandler().onClick(event, navController)
            }
        }

        composable(NavigationDestination.WeatherDashboard.fullRoute) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            WeatherDashboardScreen(viewModel)
        }
    }
}
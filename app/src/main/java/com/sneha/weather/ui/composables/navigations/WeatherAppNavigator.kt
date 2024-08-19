package com.sneha.weather.ui.composables.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.android.gms.maps.model.LatLng
import com.sneha.weather.navigation.NavigationArgumentsConstants
import com.sneha.weather.navigation.NavigationDestination
import com.sneha.weather.ui.composables.navigations.handler.OnBoardClickHandler
import com.sneha.weather.ui.composables.screens.dashboard.WeatherDashboardScreen
import com.sneha.weather.ui.composables.navigations.handler.WeatherDashboardClickHandler
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
            val viewModel = viewModel<LocationSelectionViewModel>()
            LocationSelectionScreen(viewModel) { event ->
                OnBoardClickHandler().onClick(event, navController)
            }
        }

        composable(NavigationDestination.WeatherDashboard.fullRoute) {
            val viewModel = viewModel<DashboardViewModel>()
            WeatherDashboardScreen(viewModel) { event ->
                WeatherDashboardClickHandler().onClick(event, navController)
            }
        }
    }
}
package com.sneha.weather.ui.composables.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sneha.weather.navigation.NavigationDestination

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun WeatherNavHost(
    navController: NavHostController,
    startDestination: NavigationDestination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
        builder = builder
    )
}
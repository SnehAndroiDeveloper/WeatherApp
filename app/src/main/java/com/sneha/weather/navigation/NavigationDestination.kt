package com.sneha.weather.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Created by Sneha on 15-08-2024.
 */
sealed class NavigationDestination(
    protected val route: String,
    vararg params: String
) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }


    internal fun routeWithArguments(route: String, vararg params: Pair<String, Any?>) =
        buildString {
            append(route)
            params.forEach {
                it.second?.toString()?.let { arg ->
                    val encodedValue = URLEncoder.encode(arg, StandardCharsets.UTF_8.toString())
                    this.append("/$encodedValue")
                }
            }
        }

    sealed class NoArgumentsDestination(route: String) : NavigationDestination(route) {
        operator fun invoke(): String = route
    }

    data object LocationSelection : NoArgumentsDestination(NavigationRouteConstants.ROUTE_LOCATION_SELECTION)

    data object WeatherDashboard : NoArgumentsDestination(NavigationRouteConstants.ROUTE_DASHBOARD)
}
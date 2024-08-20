package com.sneha.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.navigation.NavigationDestination
import com.sneha.weather.ui.composables.navigations.WeatherAppNavigator
import com.sneha.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                val destination =
                    if (WeatherPreferences.get(PreferenceKeys.PREF_LATITUDE, "").isNotEmpty()) {
                        NavigationDestination.WeatherDashboard
                    } else {
                        NavigationDestination.LocationSelection
                    }
                val navController = rememberNavController()
                WeatherAppNavigator(navController = navController, destination)
            }
        }
    }
}
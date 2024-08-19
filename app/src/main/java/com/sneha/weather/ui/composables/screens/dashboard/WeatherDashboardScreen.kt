package com.sneha.weather.ui.composables.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.events.DashboardClickEvents
import com.sneha.weather.ui.theme.WeatherTheme
import com.sneha.weather.viewmodels.DashboardViewModel

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun WeatherDashboardScreen(
    viewModel: DashboardViewModel,
    onClick: (DashboardClickEvents) -> Unit
) {
    val context = LocalContext.current
    var showEmptyScreen by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getUiEvent().collect {
            when (it.state) {
                UiStateEnum.SHOW_EMPTY_SCREEN -> {
                    showEmptyScreen = true
                }

                UiStateEnum.SHOW_CONTENT -> {
                    showEmptyScreen = false
                }

                else -> {
                }
            }
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        val latitude = WeatherPreferences.get(
            context, PreferenceKeys.PREF_LATITUDE, ""
        )
        val longitude = WeatherPreferences.get(
            context, PreferenceKeys.PREF_LONGITUDE, ""
        )
        viewModel.getWeatherInfo(latitude, longitude)
    }

    if (showEmptyScreen) {
        EmptyContent()
    } else {
        DashboardContent(viewModel = viewModel)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherDashboardPreview() {
    WeatherTheme {
        WeatherDashboardScreen(DashboardViewModel()) {}
    }
}
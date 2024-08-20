package com.sneha.weather.ui.composables.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.events.DashboardClickEvents
import com.sneha.weather.ui.composables.components.DashboardContent
import com.sneha.weather.ui.composables.components.EmptyContent
import com.sneha.weather.ui.composables.components.LocationInfoContent
import com.sneha.weather.ui.composables.components.NoInternetContent
import com.sneha.weather.ui.theme.BlackBackground
import com.sneha.weather.ui.theme.BlueBackground
import com.sneha.weather.ui.theme.CardBackground
import com.sneha.weather.ui.theme.WeatherTheme
import com.sneha.weather.viewmodels.DashboardViewModel
import kotlinx.coroutines.launch

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun WeatherDashboardScreen(
    viewModel: DashboardViewModel,
    onClick: (DashboardClickEvents) -> Unit
) {
    var showEmptyScreen by rememberSaveable { mutableStateOf(false) }
    val snackBarState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()

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
            PreferenceKeys.PREF_LATITUDE, ""
        )
        val longitude = WeatherPreferences.get(
            PreferenceKeys.PREF_LONGITUDE, ""
        )
        viewModel.getWeatherInfo(latitude, longitude)
    }
    if (viewModel.showNetworkAlert()) {
        coroutineScope.launch {
            snackBarState.showSnackbar("", duration = SnackbarDuration.Long)
        }
    }
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(CardBackground)
        .displayCutoutPadding(), topBar = {
        LocationInfoContent(
            modifier = Modifier
                .background(if (viewModel.weatherInfoModel.isDay) BlueBackground else BlackBackground)
                .padding(top = 10.dp, bottom = 10.dp),
            location = viewModel.weatherInfoModel.location,
            region = viewModel.weatherInfoModel.region,
            country = viewModel.weatherInfoModel.country
        )
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarState) {
            NoInternetContent(viewModel = viewModel)
        }
    }) {
        if (showEmptyScreen) {
            EmptyContent(it)
        } else {
            DashboardContent(it, viewModel = viewModel)
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherDashboardPreview() {
    WeatherTheme {
        WeatherDashboardScreen(DashboardViewModel()) {}
    }
}
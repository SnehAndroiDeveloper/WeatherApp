package com.sneha.weather.ui.composables.screens.location_selection

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sneha.weather.R
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.data.enums.UserState
import com.sneha.weather.events.LocationPopUpClickEvents
import com.sneha.weather.events.OnBoardClickEvents
import com.sneha.weather.ui.composables.components.LocationMapContent
import com.sneha.weather.ui.composables.components.ShowAlertDialog
import com.sneha.weather.ui.theme.BlueBackground
import com.sneha.weather.ui.theme.WeatherTheme
import com.sneha.weather.viewmodels.LocationSelectionViewModel

/**
 * Created by Sneha on 17-08-2024.
 */
@Composable
fun LocationSelectionScreen(
    viewModel: LocationSelectionViewModel,
    onClick: (OnBoardClickEvents) -> Unit
) {
    val context = LocalContext.current
    var userState by rememberSaveable { mutableIntStateOf(UserState.NOT_SELECTED_LOCATION.value) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getUiEvent().collect {
            when (it.state) {
                UiStateEnum.OPEN_SCREEN -> {
                    onClick(OnBoardClickEvents.OpenDashboard)
                }

                UiStateEnum.SHOW_ALERT -> {
                    userState = UserState.WEATHER_INFO_LOADED.value
                }

                UiStateEnum.HIDE_ALERT -> {
                    userState = UserState.LOADING_WEATHER_INFO.value
                }

                UiStateEnum.SHOW_TOAST -> {
                    userState = UserState.ERROR_LOADING_WEATHER_INFO.value
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_weather_info_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewModel.showNetworkAlert()) {
            ShowAlertDialog(stringResource(id = R.string.no_network_available)) {
                viewModel.hideNoInternetAlert()
            }
        }
        if (userState == UserState.NOT_SELECTED_LOCATION.value) {
            Text(
                text = stringResource(id = R.string.select_location),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = BlueBackground,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        AnimatedVisibility(
            enter = slideInVertically(),
            exit = slideOutVertically(),
            visible = userState== UserState.WEATHER_INFO_LOADED.value
        ) {
            LocationPopUpContent(weatherInfoModel = viewModel.weatherInfoModel) { event ->
                userState = UserState.LOADING_WEATHER_INFO.value
                when (event) {
                    is LocationPopUpClickEvents.OnAddLocation -> {
                        viewModel.addLocation()
                    }

                    else -> {}
                }
            }
        }
        LocationMapContent(viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDashboardPreview() {
    WeatherTheme {
        LocationSelectionScreen(viewModel = LocationSelectionViewModel()) {}
    }
}
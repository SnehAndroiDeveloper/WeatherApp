package com.sneha.weather.ui.composables.screens.location_selection

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.sneha.weather.R
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.data.enums.UserState
import com.sneha.weather.data.utils.goToAppSetting
import com.sneha.weather.events.LocationPopUpClickEvents
import com.sneha.weather.events.LocationSelectionClickEvents
import com.sneha.weather.ui.composables.components.CommonAlertDialog
import com.sneha.weather.ui.composables.components.LocationMapContent
import com.sneha.weather.ui.composables.components.LocationPopUpContent
import com.sneha.weather.ui.composables.components.MapToolbar
import com.sneha.weather.ui.composables.components.NoInternetAlert
import com.sneha.weather.ui.theme.WeatherTheme
import com.sneha.weather.viewmodels.LocationSelectionViewModel

/**
 * Created by Sneha on 17-08-2024.
 */
@Composable
fun LocationSelectionScreen(
    viewModel: LocationSelectionViewModel,
    onClick: (LocationSelectionClickEvents) -> Unit
) {
    val context = LocalContext.current
    var userState by rememberSaveable { mutableIntStateOf(UserState.NOT_SELECTED_LOCATION.value) }
    var showPermissionDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var hasLocationPermission by rememberSaveable {
        mutableStateOf(checkForPermission(context))
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = hasLocationPermission
            )
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showPermissionDialog = true
        }
        hasLocationPermission = isGranted
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = hasLocationPermission
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        hasLocationPermission = checkForPermission(context = context)
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = hasLocationPermission
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getUiEvent().collect {
            when (it.state) {
                UiStateEnum.OPEN_SCREEN -> {
                    onClick(LocationSelectionClickEvents.OpenDashboard)
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        if (showPermissionDialog) {
            CommonAlertDialog(
                onDismissRequest = {
                    showPermissionDialog = false
                },
                onConfirmation = {
                    showPermissionDialog = false
                    context.goToAppSetting()
                },
                dialogTitle = stringResource(id = R.string.allow_location_permission_title),
                dialogText = stringResource(id = R.string.allow_location_permission_message),
                positiveText = stringResource(id = R.string.allow_location_permission_positive_text),
                negativeText = stringResource(id = R.string.allow_location_permission_negative_text),
            )
        }

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.showNetworkAlert()) {
                NoInternetAlert(stringResource(id = R.string.no_network_available)) {
                    viewModel.hideNoInternetAlert()
                }
            }
            MapToolbar(
                isLocationPermissionEnabled = hasLocationPermission,
                onClick = onClick,
                askLocationPermission = {
                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                })

            AnimatedVisibility(
                enter = slideInVertically(),
                exit = slideOutVertically(),
                visible = userState == UserState.WEATHER_INFO_LOADED.value
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
}

@Preview(showBackground = true)
@Composable
fun WeatherDashboardPreview() {
    WeatherTheme {
        LocationSelectionScreen(viewModel = LocationSelectionViewModel()) {}
    }
}

fun checkForPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
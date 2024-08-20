package com.sneha.weather.ui.composables.components

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sneha.weather.ui.composables.screens.location_selection.checkForPermission
import com.sneha.weather.viewmodels.LocationSelectionViewModel

/**
 * Created by Sneha on 19-08-2024.
 */
@Composable
fun LocationMapContent(
    viewModel: LocationSelectionViewModel,
    mapProperties: MapProperties,
) {
    val context = LocalContext.current
    val mumbai = LatLng(19.0760, 72.8777)
    val initialZoom = 9f
    val finalZoom = 11f

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.latLng ?: mumbai, initialZoom)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
        onMyLocationButtonClick = {
            val latLng = getUserCurrentLatLng(context)
            latLng?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, finalZoom)
                println("Current location : ${it.latitude}, ${it.longitude}")
                viewModel.getWeatherInfo(it)
            }
            false
        },
        onMapClick = {
            println("Clicked location : ${it.latitude}, ${it.longitude}")
            viewModel.getWeatherInfo(it)
        }
    ) {
        viewModel.latLng?.let {
            Marker(
                state = MarkerState(position = it)
            )
        }
    }
}

fun getUserCurrentLatLng(context: Context): LatLng? {
    return if (checkForPermission(context = context)) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val location: Location? =
            locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        location?.let {
            LatLng(it.latitude, it.longitude)
        }
    } else {
        null
    }
}

package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sneha.weather.viewmodels.LocationSelectionViewModel

/**
 * Created by Sneha on 19-08-2024.
 */
@Composable
fun LocationMapContent(viewModel: LocationSelectionViewModel) {
    val mumbai = LatLng(19.0760, 72.8777)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.latLng ?: mumbai, 9f)
    }
    GoogleMap(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp)),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false),
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
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
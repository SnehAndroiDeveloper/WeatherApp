package com.sneha.weather.events

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Sneha on 17-08-2024.
 */
sealed class LocationPopUpClickEvents {
    data object OnDismiss : LocationPopUpClickEvents()
    data class OnAddLocation(val latLng: LatLng) : LocationPopUpClickEvents()
}
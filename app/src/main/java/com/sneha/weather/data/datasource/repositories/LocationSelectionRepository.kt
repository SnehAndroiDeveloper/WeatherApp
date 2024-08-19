package com.sneha.weather.data.datasource.repositories

import com.google.android.gms.maps.model.LatLng
import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.WeatherApplication
import com.sneha.weather.data.data_type.LocationSelectionDataType
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import com.sneha.weather.data.datasource.network.controllers.WeatherInfoNetworkController
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.events.DataEvent
import kotlinx.coroutines.flow.flow

/**
 * Created by Sneha on 18-08-2024.
 */
class LocationSelectionRepository : BaseRepository() {
    private val networkController by lazy { WeatherInfoNetworkController() }
    private val dbController by lazy { WeatherDBController() }

    suspend fun getWeatherInfo(latitude: Double, longitude: Double) = flow {
        emit(
            DataEvent(
                state = DataStateEnum.OperationStart,
                dataType = LocationSelectionDataType.FetchWeatherInfo
            )
        )
        val result = networkController.getCurrentWeatherInfo("$latitude,$longitude")

        emitNetworkResult(
            networkController,
            this,
            result,
            LocationSelectionDataType.FetchWeatherInfo
        ) {
        }
        emit(
            DataEvent(
                state = DataStateEnum.OperationEnd,
                dataType = LocationSelectionDataType.FetchWeatherInfo
            )
        )
    }

    suspend fun addLocation(weatherEntity: WeatherEntity) {
        WeatherApplication.instance?.applicationContext?.let {
            WeatherPreferences.save(
                it, PreferenceKeys.PREF_LATITUDE, weatherEntity.latitude
            )
            WeatherPreferences.save(
                it, PreferenceKeys.PREF_LONGITUDE, weatherEntity.longitude
            )
        }
        dbController.insertWeatherEntity(weatherEntity)
    }
}
package com.sneha.weather.data.datasource.repositories

import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.data.data_type.LocationSelectionDataType
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import com.sneha.weather.data.datasource.network.controllers.WeatherInfoNetworkController
import com.sneha.weather.data.datasource.pref.PreferenceKeys
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.events.DataEvent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Sneha on 18-08-2024.
 */
class LocationSelectionRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var networkController: WeatherInfoNetworkController

    @Inject
    lateinit var dbController: WeatherDBController

    @Inject
    lateinit var weatherPreferences: WeatherPreferences

    /**
     * Fetches weather information for the specified latitude and longitude.
     *
     * This function emits a flow of [DataEvent] objects,
     * representing the different states of the weather information fetching process.
     * It first emits an [DataStateEnum.OperationStart] event,
     * then fetches the current weather information from the network using [networkController].
     * The result of the network call is then processed using [emitNetworkResult],
     * which handles network success,failure, and no network connection scenarios.
     * Finally, it emits an [DataStateEnum.OperationEnd] event.
     *
     * @param latitude The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return A flow of [DataEvent] objects.
     */
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

    /**
     * Adds a new location to the weather data store.
     *
     * This function saves the latitude and longitude of the new location to shared preferences
     * and inserts the weather entity into the database.
     *
     * @param weatherEntity The weather entity representing the new location.
     */
    suspend fun addLocation(weatherEntity: WeatherEntity) {
        weatherPreferences.save(
            PreferenceKeys.PREF_LATITUDE, weatherEntity.latitude
        )
        weatherPreferences.save(
            PreferenceKeys.PREF_LONGITUDE, weatherEntity.longitude
        )
        dbController.insertWeatherEntity(weatherEntity)
    }
}
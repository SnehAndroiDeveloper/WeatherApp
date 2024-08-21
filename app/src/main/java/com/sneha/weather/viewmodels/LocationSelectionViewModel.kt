package com.sneha.weather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sneha.weather.data.data_type.LocationSelectionDataType
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel
import com.sneha.weather.data.datasource.repositories.LocationSelectionRepository
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.data.events.UiEvent
import com.sneha.weather.data.models.WeatherInfoModel
import com.sneha.weather.data.models.convertToWeatherEntity
import com.sneha.weather.data.models.convertToWeatherInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Sneha on 18-08-2024.
 */
@HiltViewModel
class LocationSelectionViewModel @Inject constructor() :
    BaseViewModel<LocationSelectionDataType>() {
    @Inject
    lateinit var repository: LocationSelectionRepository
    private val defaultScope by lazy { CoroutineScope(Dispatchers.Default) }
    var weatherInfoModel by mutableStateOf(WeatherInfoModel())
        private set
    var latLng by mutableStateOf<LatLng?>(null)
    private var job: Job? = null

    /**
     * Fetches and processes weather information for the specified LatLng coordinates.
     *
     * This function cancels any existing job ([job]) and
     * launches a new coroutine to collect weather information
     * from the repository's [getWeatherInfo] flow.
     *
     * It handles different data states and UI events:
     * - [DataStateEnum.OperationStart]:
     *   Resets the [weatherInfoModel] and [latLng] properties,
     *   and sets a [UiEvent]to hide any existing alerts.
     * - [DataStateEnum.NoNetworkConnection]:
     *   Calls [showNoInternetAlert] to display a "no internet" alert.
     * - [DataStateEnum.NetworkSuccess]:
     *   If network data is successfully retrieved and there are no errors,
     *   it converts the data to a [WeatherInfoModel],
     *   updates the ViewModel's properties,
     *   and sets a [UiEvent] to show an alert with the weather information.
     *   If there's an error in the response,
     *   it sets a [UiEvent] to show a toast message.
     *
     * The coroutine is launched in the [viewModelScope].
     *
     * @param latLng The LatLng coordinates for which to fetch weather information.
     */
    fun getWeatherInfo(latLng: LatLng) {
        job?.cancel()
        job = defaultScope.launch {
            repository.getWeatherInfo(latLng.latitude, latLng.longitude).onEach {
                if (it.dataType == LocationSelectionDataType.FetchWeatherInfo) {
                    when (it.state) {
                        DataStateEnum.OperationStart -> {
                            this@LocationSelectionViewModel.weatherInfoModel =
                                WeatherInfoModel()
                            this@LocationSelectionViewModel.latLng = null
                            setUiEvent(
                                UiEvent(
                                    state = UiStateEnum.HIDE_ALERT,
                                    dataType = it.dataType
                                )
                            )
                        }

                        DataStateEnum.NoNetworkConnection -> {
                            showNoInternetAlert()
                        }

                        DataStateEnum.NetworkSuccess -> {
                            if (it.data is WeatherInfoResponseModel) {
                                if (it.data.error == null) {
                                    val weatherInfoModel = it.data.convertToWeatherInfoModel()
                                    this@LocationSelectionViewModel.weatherInfoModel =
                                        weatherInfoModel
                                    this@LocationSelectionViewModel.latLng = latLng
                                    setUiEvent(
                                        UiEvent(
                                            state = UiStateEnum.SHOW_ALERT,
                                            dataType = it.dataType
                                        )
                                    )
                                } else {
                                    setUiEvent(
                                        UiEvent(
                                            state = UiStateEnum.SHOW_TOAST,
                                            dataType = it.dataType
                                        )
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    /**
     * Adds the current weather information as a new location to the repository.
     *
     * This function launches a coroutine that performs the following steps:
     * 1. Converts the [weatherInfoModel] to a [WeatherEntity].
     * 2. Calls the [repository.addLocation] function to add the entity
     *    to the data store.
     * 3. Sets a [UiEvent] to trigger the opening of a new screen
     *    (presumably to display the added location).
     *
     * The coroutine is launched in the [defaultScope].
     */
    fun addLocation() {
        defaultScope.launch {
            val weatherEntity = weatherInfoModel.convertToWeatherEntity()
            repository.addLocation(weatherEntity)
            setUiEvent(
                UiEvent(
                    dataType = LocationSelectionDataType.AddLocation,
                    state = UiStateEnum.OPEN_SCREEN
                )
            )
        }
    }
}
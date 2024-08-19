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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Sneha on 18-08-2024.
 */
class LocationSelectionViewModel : BaseViewModel<LocationSelectionDataType>() {
    private val repository by lazy { LocationSelectionRepository() }
    private val defaultScope by lazy { CoroutineScope(Dispatchers.Default) }
    var weatherInfoModel by mutableStateOf(WeatherInfoModel())
        private set
    var latLng by mutableStateOf<LatLng?>(null)
    var job: Job? = null

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
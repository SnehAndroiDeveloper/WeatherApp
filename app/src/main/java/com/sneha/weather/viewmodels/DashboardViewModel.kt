package com.sneha.weather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.data.data_type.DashboardDataType
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel
import com.sneha.weather.data.datasource.pref.WeatherPreferences
import com.sneha.weather.data.datasource.repositories.DashboardRepository
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.enums.UiStateEnum
import com.sneha.weather.data.events.UiEvent
import com.sneha.weather.data.models.WeatherInfoModel
import com.sneha.weather.data.models.convertToWeatherInfoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Sneha on 15-08-2024.
 */
class DashboardViewModel : BaseViewModel<DashboardDataType>() {
    private val repository by lazy { DashboardRepository() }
    private val defaultScope by lazy { CoroutineScope(Dispatchers.Default) }
    var weatherInfoModel by mutableStateOf(WeatherInfoModel())
        private set

    /**
     * Fetches and processes weather information for the given
     * @param latitude and
     * @param longitude of the location.
     *
     * This function launches a coroutine
     * to collect weather information from the repository's [getCurrentLocationWeatherInfo] flow.
     * It handles different data states and UI events:
     * - [DataStateEnum.DBConnectionEnd]:
     *      If data is available from the database,
     *      it converts it to a [WeatherInfoModel] and updates the ViewModel's [weatherInfoModel].
     *      If no details found, it sets a [UiEvent] to show an empty screen.
     * - [DataStateEnum.NetworkSuccess]:
     *      If network data is successfully retrieved,
     *      it converts it to a [WeatherInfoModel], updates the ViewModel's [weatherInfoModel],
     *      and sets a [UiEvent] to show the content.
     * - [DataStateEnum.NoNetworkConnection]:
     *      If there's no network connection, it calls [showNoInternetAlert].
     */
    fun getWeatherInfo(latitude: String, longitude: String) {
        defaultScope.launch {
            repository.getCurrentLocationWeatherInfo(latitude, longitude)
                .onEach {
                    if (it.dataType == DashboardDataType.FetchCurrentWeather) {
                        when (it.state) {
                            DataStateEnum.DBConnectionEnd -> {
                                if (it.data is WeatherEntity) {
                                    val weatherInfoModel = it.data.convertToWeatherInfoModel()
                                    this@DashboardViewModel.weatherInfoModel = weatherInfoModel
                                } else {
                                    setUiEvent(
                                        UiEvent(
                                            state = UiStateEnum.SHOW_EMPTY_SCREEN,
                                            dataType = it.dataType
                                        )
                                    )
                                }
                            }

                            DataStateEnum.NetworkSuccess -> {
                                if (it.data is WeatherInfoResponseModel) {
                                    val weatherInfoModel = it.data.convertToWeatherInfoModel()
                                    this@DashboardViewModel.weatherInfoModel = weatherInfoModel
                                    setUiEvent(
                                        UiEvent(
                                            state = UiStateEnum.SHOW_CONTENT,
                                            dataType = it.dataType
                                        )
                                    )
                                }
                            }

                            DataStateEnum.NoNetworkConnection -> {
                                showNoInternetAlert()
                            }

                            else -> {
//                            NO OPERATION REQUIRED
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
}
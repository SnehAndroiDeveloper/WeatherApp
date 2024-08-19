package com.sneha.weather.data.datasource.repositories

import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.data.data_type.DashboardDataType
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import com.sneha.weather.data.datasource.network.controllers.SunInfoNetworkController
import com.sneha.weather.data.datasource.network.controllers.WeatherInfoNetworkController
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.events.DataEvent
import com.sneha.weather.data.models.convertToWeatherEntity
import com.sneha.weather.data.utils.convertDateToTimeFormat
import com.sneha.weather.data.utils.toDate
import kotlinx.coroutines.flow.flow

/**
 * Created by Sneha on 15-08-2024.
 */
class DashboardRepository : BaseRepository() {
    private val weatherInfoNetworkController by lazy { WeatherInfoNetworkController() }
    private val sunInfoNetworkController by lazy { SunInfoNetworkController() }
    private val dbController by lazy { WeatherDBController() }

    /**
     * Retrieves and emits current weather information for the specified latitude and longitude as a flow,
     * including database interaction and network updates.*
     * This function performs the following steps:
     * 1. Emits a DataEvent signaling the start of the operation.
     * 2. Emits a DataEvent signaling the start of the database connection.
     * 3. Fetches weather information from the database using [fetchWeatherInfoFromDB] and emits a DataEvent with the result.
     * 4. Fetches current weather information from the network using [weatherInfoNetworkController.getCurrentWeatherInfo].
     * 5. Emits the network result using [emitNetworkResult].
     * 6. Fetches sunrise and sunset information from the network using [sunInfoNetworkController.getCurrentSunInfo].
     * 7. Emits the network result using [emitNetworkResult] and updates the weather information with sunrise and sunset times.
     * 8. Inserts the updated weather information into the database using [performInsertDBOperation].
     * 9. Emits a DataEvent signaling the end of the operation with the final weather information.
     *
     * @param latitude The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return A flow emitting DataEvent and network result objects.
     */
    suspend fun getCurrentLocationWeatherInfo(latitude: String, longitude: String) = flow {
        var weatherInfoResponseModel: WeatherInfoResponseModel? = null
        emit(
            DataEvent(
                state = DataStateEnum.OperationStart,
                dataType = DashboardDataType.FetchCurrentWeather
            )
        )

        emit(
            DataEvent(
                state = DataStateEnum.DBConnectionStart,
                dataType = DashboardDataType.FetchCurrentWeather
            )
        )

        emit(
            DataEvent(
                state = DataStateEnum.DBConnectionEnd,
                dataType = DashboardDataType.FetchCurrentWeather,
                data = fetchWeatherInfoFromDB(latitude, longitude)
            )
        )
        val query = "$latitude,$longitude"
        val result = weatherInfoNetworkController.getCurrentWeatherInfo(query)

        emitNetworkResult(
            weatherInfoNetworkController,
            this,
            result,
            DashboardDataType.FetchCurrentWeather) { response ->
            response?.let {
                if (it.error == null) {
                    weatherInfoResponseModel = it
                }
            }
        }
        val sunResult = sunInfoNetworkController.getCurrentSunInfo(
            lat = latitude.toDouble(),
            lng = longitude.toDouble()
        )
        emitNetworkResult(
            sunInfoNetworkController,
            this,
            sunResult,
            DashboardDataType.FetchCurrentWeather
        ) { response ->
            response?.let {
                weatherInfoResponseModel?.location?.timezone_id?.let { timeZone ->
                    weatherInfoResponseModel?.sunRise =
                        it.results.sunrise.toDate().convertDateToTimeFormat(timeZone)
                    weatherInfoResponseModel?.sunSet =
                        it.results.sunset.toDate().convertDateToTimeFormat(timeZone)
                }
            }
        }
        weatherInfoResponseModel?.let { performInsertDBOperation(it) }
        emit(
            DataEvent(
                state = DataStateEnum.OperationEnd,
                dataType = DashboardDataType.FetchCurrentWeather,
                data = weatherInfoResponseModel
            )
        )
    }


    /**
     * Fetches weather information from the database based on latitude and longitude.
     *
     * @param latitude The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return The [WeatherEntity] corresponding to the coordinates, or null if not found.
     */
    private suspend fun fetchWeatherInfoFromDB(
        latitude: String,
        longitude: String
    ): WeatherEntity? {
        return dbController.getWeatherEntity(latitude, longitude)
    }

    /**
     * Inserts weather information into the database.
     *
     * This function converts a [WeatherInfoResponseModel] to a [WeatherEntity]
     * and inserts it into the database using [dbController.insertWeatherEntity].
     *
     * @param weatherInfoResponseModel The weather information response model to insert.
     */
    private suspend fun performInsertDBOperation(weatherInfoResponseModel: WeatherInfoResponseModel) {
        val entity = weatherInfoResponseModel.convertToWeatherEntity()
        dbController.insertWeatherEntity(entity)
    }
}
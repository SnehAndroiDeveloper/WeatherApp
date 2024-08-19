package com.sneha.weather.data.datasource.repositories

import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.data.data_type.DashboardDataType
import com.sneha.weather.data.datasource.local_db.controllers.WeatherDBController
import com.sneha.weather.data.datasource.network.controllers.WeatherInfoNetworkController
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.events.DataEvent
import com.sneha.weather.data.models.convertToWeatherEntity
import kotlinx.coroutines.flow.flow

/**
 * Created by Sneha on 15-08-2024.
 */
class DashboardRepository : BaseRepository() {
    private val networkController by lazy { WeatherInfoNetworkController() }
    private val dbController by lazy { WeatherDBController() }

    /**
     * Retrieves and emits current weather information for the specified query as a flow,
     * including database interaction and network updates.
     ** This function performs the following steps:
     * 1. Emits a DataEvent signaling the start of the operation.
     * 2. Emits a DataEvent signaling the start of the database connection.
     * 3. Fetches weather information from the database using [fetchWeatherInfoFromDB] and emits a DataEvent with the result.
     * 4. Fetches current weather information from the network using [networkController.getCurrentWeatherInfo].
     * 5. Emits the network result using [emitNetworkResult], and if successful, inserts the response into the database using [performInsertDBOperation].
     * 6. Emits a DataEvent signaling the end of the operation.
     *
     * @param latitude The latitude coordinate to get weather data for.
     * @param longitude The longitude coordinate to get weather data for.
     * @return A flow emitting DataEvent and network result objects.
     */
    suspend fun getCurrentLocationWeatherInfo(latitude: String, longitude: String) = flow {
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
        val result = networkController.getCurrentWeatherInfo(query)

        emitNetworkResult(
            networkController,
            this,
            result,
            DashboardDataType.FetchCurrentWeather
        ) { response ->
            response?.let {
                performInsertDBOperation(it)
            }
        }

        emit(
            DataEvent(
                state = DataStateEnum.OperationEnd,
                dataType = DashboardDataType.FetchCurrentWeather
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
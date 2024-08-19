package com.sneha.weather.data.datasource.network.controllers

import com.sneha.network.RequestMethodType
import com.sneha.network.api.ApiRequestData
import com.sneha.weather.data.datasource.network.constants.EndPointConstants
import com.sneha.weather.data.datasource.network.constants.ParameterConstants
import com.sneha.weather.data.datasource.network.constants.URLConstants
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel

/**
 * Created by Sneha on 15-08-2024.
 */
class WeatherInfoNetworkController : BaseNetworkController(URLConstants.WEATHER_INFO_BASE_URL) {

    /**
     * Retrieves current weather information for the specified query.
     *
     * @param query The location or city name to get weather data for.
     * @return The weather information for the query, parsed into a WeatherInfoResponseModel object.
     */
    suspend fun getCurrentWeatherInfo(query: String) = networkManager.makeNetworkRequest(
        apiRequestData = ApiRequestData(
            networkResponseModel = WeatherInfoResponseModel::class.java,
            endpoint = EndPointConstants.WS_CURRENT,
            requestMethodType = RequestMethodType.GET,
            queryParams = hashMapOf(
                ParameterConstants.PARAM_QUERY to query,
                ParameterConstants.PARAM_ACCESS_KEY to ParameterConstants.PARAM_VALUE_ACCESS_KEY
            )
        )
    )
}
package com.sneha.weather.data.datasource.network.controllers

import com.sneha.network.RequestMethodType
import com.sneha.network.api.ApiRequestData
import com.sneha.weather.data.datasource.network.constants.EndPointConstants
import com.sneha.weather.data.datasource.network.constants.ParameterConstants
import com.sneha.weather.data.datasource.network.constants.URLConstants
import com.sneha.weather.data.datasource.network.models.response.sun_info.SunInfoResponseModel
import javax.inject.Inject

/**
 * Created by Sneha on 15-08-2024.
 */
class SunInfoNetworkController @Inject constructor() :
    BaseNetworkController(URLConstants.SUN_INFO_BASE_URL) {

    /**
     * Retrieves sunrise and sunset information for the specified latitude and longitude.
     *
     * @param lat The latitude coordinate.
     * @param lng The longitude coordinate.
     * @return The sunrise and sunset information for the coordinates, parsed into a SunInfoResponseModel object.
     */
    suspend fun getCurrentSunInfo(lat: Double, lng: Double) = networkManager.makeNetworkRequest(
        apiRequestData = ApiRequestData(
            networkResponseModel = SunInfoResponseModel::class.java,
            endpoint = EndPointConstants.WS_JSON,
            requestMethodType = RequestMethodType.GET,
            queryParams = hashMapOf(
                ParameterConstants.PARAM_LAT to lat,
                ParameterConstants.PARAM_LNG to lng
            )
        )
    )
}
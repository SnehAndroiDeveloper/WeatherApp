package com.sneha.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sneha.network.api.ApiRequestData
import com.sneha.network.api.NetworkInteraction
import com.sneha.network.api.NetworkInterface

/**
 * @author Created by Sneha on 15/08/2024
 */
class NetworkManager(
    baseURL: String = "",
    timeoutTime: Long = NetworkUtils.NETWORK_TIMEOUT_TIME
) :
    NetworkInteraction<NetworkInterface>(
        baseURL = baseURL,
        timeOutTime = timeoutTime,
        mInterface = NetworkInterface::class.java
    ) {

    /**
     * [makeNetworkRequest] for making network request
     * @param [apiRequestData] data class for requesting API (Required)
     */
    suspend fun <ResponseModel> makeNetworkRequest(
        apiRequestData: ApiRequestData<ResponseModel>
    ) = performNetworkOperation(apiRequestData.networkResponseModel) {
        when (apiRequestData.requestMethodType) {
            RequestMethodType.GET -> {
                if (apiRequestData.queryParams != null)
                    getNetworkInterface(apiRequestData.requestHeaders).getRequest(
                        apiRequestData.endpoint,
                        apiRequestData.queryParams
                    )
                else
                    getNetworkInterface(apiRequestData.requestHeaders).getRequest(
                        apiRequestData.endpoint
                    )
            }

            RequestMethodType.POST -> {
                when {
                    apiRequestData.isFormUrlEncoded -> {
                        getNetworkInterface(apiRequestData.requestHeaders).postFormUrlEncodedRequest(
                            apiRequestData.endpoint,
                            apiRequestData.bodyMapParams
                        )
                    }

                    apiRequestData.queryParams != null -> {
                        getNetworkInterface(apiRequestData.requestHeaders).postRequest(
                            apiRequestData.endpoint,
                            apiRequestData.requestBody,
                            apiRequestData.queryParams!!
                        )
                    }

                    else -> {
                        getNetworkInterface(apiRequestData.requestHeaders).postRequest(
                            apiRequestData.endpoint,
                            apiRequestData.requestBody
                        )
                    }
                }
            }

            RequestMethodType.PUT -> {
                getNetworkInterface(apiRequestData.requestHeaders).putRequest(
                    apiRequestData.endpoint,
                    apiRequestData.requestBody
                )
            }

            RequestMethodType.DELETE -> {
                when {
                    apiRequestData.queryParams != null -> {
                        getNetworkInterface(apiRequestData.requestHeaders).deleteRequest(
                            apiRequestData.endpoint,
                            apiRequestData.queryParams
                        )
                    }

                    apiRequestData.requestBody != null -> {
                        getNetworkInterface(apiRequestData.requestHeaders).deleteBodyRequest(
                            apiRequestData.endpoint,
                            apiRequestData.requestBody
                        )
                    }

                    else -> {
                        getNetworkInterface(apiRequestData.requestHeaders).deleteRequest(
                            apiRequestData.endpoint,
                            apiRequestData.queryParams
                        )
                    }
                }
            }

            RequestMethodType.PATCH -> {
                getNetworkInterface(apiRequestData.requestHeaders).patchRequest(
                    apiRequestData.endpoint,
                    apiRequestData.requestBody
                )
            }
        }
    }

    /**
     * [makeInputStreamRequest] for making network request for getting input stream
     * @param [apiRequestData] data class for requesting API (Required)
     */
    suspend fun makeInputStreamRequest(
        apiRequestData: ApiRequestData<Any>
    ) = performInputStreamNetworkOperation {
        when (apiRequestData.requestMethodType) {
            RequestMethodType.GET -> {
                getNetworkInterface(apiRequestData.requestHeaders).getInputStreamRequest(
                    apiRequestData.endpoint,
                    apiRequestData.queryParams
                )
            }

            else -> {
                getNetworkInterface(apiRequestData.requestHeaders).getRequest(
                    apiRequestData.endpoint
                )
            }
        }
    }

    /**
     * Method to check network connectivity
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val actualNetwork =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}
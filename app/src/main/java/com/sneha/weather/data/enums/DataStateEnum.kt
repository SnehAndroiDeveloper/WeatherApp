package com.sneha.weather.data.enums

/**
 * Created by Sneha on 15-08-2024.
 */
enum class DataStateEnum {
    OperationStart,
    NetworkConnectionStart,
    NetworkException,
    NetworkSuccess,
    NetworkFailure,
    NoNetworkConnection,
    DBConnectionStart,
    DBConnectionEnd,
    OperationEnd
}
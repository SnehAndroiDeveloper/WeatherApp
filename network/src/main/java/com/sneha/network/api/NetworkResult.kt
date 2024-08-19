package com.sneha.network.api

/**
 * @author Created by Sneha on 15/08/2024
 */
sealed class NetworkResult<out R> {
    data class NetworkSuccess<out T>(val code: Int, val data: T? = null) : NetworkResult<T>()
    data class NetworkError<out T>(val code: Int, val message: String?) : NetworkResult<T>()
    data class NetworkException<out T>(val e: Throwable) : NetworkResult<T>()

    override fun toString(): String {
        return when (this) {
            is NetworkSuccess<*> -> "Success[data=$data]"
            is NetworkError<*> -> "Error[code=$code]"
            is NetworkException<*> -> "Exception[message=${e.message}]"
        }
    }
}
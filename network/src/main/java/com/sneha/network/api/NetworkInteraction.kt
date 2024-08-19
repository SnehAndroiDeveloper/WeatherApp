package com.sneha.network.api

import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

/**
 * @author Created by Sneha on 15/08/2024
 */
abstract class NetworkInteraction<Interface>(
    private val baseURL: String,
    private val timeOutTime: Long,
    private val mInterface: Class<Interface>
) {

    /**
     * [getNetworkInterface] for getting the network interface
     * @param [headers] Headers for network operation (Optional)
     */
    fun getNetworkInterface(headers: HashMap<String, String>? = null): Interface =
        NetworkBuilder.getNetworkInstance(
            baseUrl = baseURL,
            headersMap = headers,
            timeOutTime = timeOutTime
        ).create(mInterface)

    /**
     * [performNetworkOperation] for perform the network operation
     * @param [execute] suspend function
     */
    suspend fun <T, ResponseModel> performNetworkOperation(
        networkResponseModel: Class<ResponseModel>? = null,
        execute: suspend () -> Response<T>
    ) = flow {
        val networkResult = try {
            val response = execute()
            if (response.isSuccessful) {
                val body = response.body()
                val bodyToResponseModel = Gson().fromJson(body as JsonElement, networkResponseModel)
                if (bodyToResponseModel != null)
                    NetworkResult.NetworkSuccess(
                        code = response.code(),
                        data = bodyToResponseModel
                    )
                else
                    NetworkResult.NetworkSuccess(code = response.code())
            } else {
                val errorBody = response.errorBody()?.string()
                val errorBodyObject = errorBody?.let { JSONObject(it) }
                if (errorBodyObject != null)
                    NetworkResult.NetworkError(
                        code = response.code(),
                        message = errorBodyObject.optString("message", "")
                    )
                else
                    NetworkResult.NetworkError(
                        code = response.code(),
                        message = response.message()
                    )
            }
        } catch (e: HttpException) {
            NetworkResult.NetworkException(e)
        } catch (e: Throwable) {
            NetworkResult.NetworkException(e)
        }
        emit(networkResult)
    }

    /**
     * [performInputStreamNetworkOperation] for perform the network operation for getting input stream
     * @param [execute] suspend function
     */
    suspend fun <T> performInputStreamNetworkOperation(
        execute: suspend () -> Response<T>
    ) = flow {
        val networkResult = try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful) {
                if (body != null)
                    NetworkResult.NetworkSuccess(
                        code = response.code(),
                        data = (body as ResponseBody).byteStream()
                    )
                else
                    NetworkResult.NetworkError(
                        code = response.code(),
                        message = response.message()
                    )
            } else {
                NetworkResult.NetworkError(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: HttpException) {
            NetworkResult.NetworkException(e)
        } catch (e: Throwable) {
            NetworkResult.NetworkException(e)
        }
        emit(networkResult)
    }
}
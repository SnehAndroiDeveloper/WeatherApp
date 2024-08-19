package com.sneha.weather.data.datasource.repositories

import com.sneha.network.api.NetworkResult
import com.sneha.weather.data.datasource.network.controllers.BaseNetworkController
import com.sneha.weather.data.enums.DataStateEnum
import com.sneha.weather.data.events.DataEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * Created by Sneha on 15-08-2024.
 */
open class BaseRepository : CoroutineScope {
    /**
     * [emitNetworkResult] is responsible to emitting the network result.
     * Also, checks whether it's a success/failure/exception.
     */
    protected suspend fun <T : Any, R> emitNetworkResult(
        baseNetworkController: BaseNetworkController,
        flowCollector: FlowCollector<DataEvent<R>>,
        result: Flow<NetworkResult<T>>,
        dataType: R,
        onNetworkSuccess: suspend (T?) -> Unit
    ) {
        if (baseNetworkController.isInternetAvailable() == true) {
            flowCollector.emit(
                DataEvent(
                    state = DataStateEnum.NetworkConnectionStart,
                    dataType = dataType
                )
            )
            result.flowOn(coroutineContext).catch { e ->
                flowCollector.emit(
                    DataEvent(
                        state = DataStateEnum.NetworkException,
                        message = e.message,
                        dataType = dataType
                    )
                )
            }.collect {
                when (it) {
                    is NetworkResult.NetworkSuccess -> {
                        flowCollector.emit(
                            DataEvent(
                                state = DataStateEnum.NetworkSuccess,
                                data = it.data,
                                dataType = dataType
                            )
                        )

                        onNetworkSuccess(it.data)
                    }

                    is NetworkResult.NetworkError -> {
                        flowCollector.emit(
                            DataEvent(
                                state = DataStateEnum.NetworkFailure,
                                code = it.code,
                                message = it.message,
                                dataType = dataType
                            )
                        )
                    }

                    is NetworkResult.NetworkException -> {
                        flowCollector.emit(
                            DataEvent(
                                state = DataStateEnum.NetworkException,
                                message = it.e.message,
                                dataType = dataType
                            )
                        )
                    }
                }
            }
        } else {
            flowCollector.emit(
                DataEvent(
                    state = DataStateEnum.NoNetworkConnection,
                    dataType = dataType
                )
            )
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
package com.sneha.weather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sneha.weather.data.events.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Created by Sneha on 15-08-2024.
 */
open class BaseViewModel<T> : ViewModel() {
    private var showDialog by mutableStateOf(false)
    private val _uiEvent = MutableSharedFlow<UiEvent<T>>()

    protected suspend fun setUiEvent(uiEvent: UiEvent<T>) =
        _uiEvent.emit(uiEvent)

    fun getUiEvent() = _uiEvent.asSharedFlow()

    protected fun showNoInternetAlert() {
        showDialog = true
    }

    fun showNetworkAlert() = showDialog

    fun hideNoInternetAlert() {
        showDialog = false
    }
}
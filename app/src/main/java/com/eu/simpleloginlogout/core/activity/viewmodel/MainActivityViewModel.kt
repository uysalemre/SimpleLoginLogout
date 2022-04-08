package com.eu.simpleloginlogout.core.activity.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * MainActivityViewModel manages loading state
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _loadingEvent = MutableStateFlow(false)
    val loadingEvent: StateFlow<Boolean> get() = _loadingEvent

    fun isLoadingVisible(isVisible: Boolean) {
        _loadingEvent.value = isVisible
    }
}
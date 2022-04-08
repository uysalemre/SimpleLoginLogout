package com.eu.simpleloginlogout.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel
import com.eu.simpleloginlogout.core.network.NetworkResult
import com.eu.simpleloginlogout.core.persistance.IDataStore
import com.eu.simpleloginlogout.profile.repository.ProfileRepository
import com.eu.simpleloginlogout.profile.viewmodel.intent.ProfileIntent
import com.eu.simpleloginlogout.profile.viewmodel.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * ProfileViewModel that manages uiStates for ProfileFragment
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val dataStore: IDataStore
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Init)
    val uiState: StateFlow<ProfileState> get() = _uiState

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init {
        dispatchIntent(ProfileIntent.GetUser)
    }

    fun dispatchIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.GetUser -> viewModelScope.launch {
                repository.getUser().collect {
                    _isRefreshing.value = false
                    when (it) {
                        NetworkResult.OnLoading -> _uiState.value = ProfileState.Loading
                        is NetworkResult.OnNotAuthorized -> _uiState.value =
                            ProfileState.UnAuthorizedAccess
                        is NetworkResult.OnSuccess -> _uiState.value = ProfileState.Success(it.data)
                        is NetworkResult.OnUnexpected -> _uiState.value =
                            ProfileState.Error(it.messageId)
                    }
                }
            }
            ProfileIntent.LogoutUser -> viewModelScope.launch {
                dataStore.saveUserToPreferencesStore(AuthResponseModel("", ""))
                _uiState.value = ProfileState.Logout
            }
            ProfileIntent.SwipeAndRefresh -> {
                _isRefreshing.value = true
                dispatchIntent(ProfileIntent.GetUser)
            }
        }
    }
}
package com.eu.simpleloginlogout.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eu.simpleloginlogout.auth.repository.AuthRepository
import com.eu.simpleloginlogout.auth.viewmodel.intent.AuthIntent
import com.eu.simpleloginlogout.auth.viewmodel.state.AuthState
import com.eu.simpleloginlogout.core.network.NetworkResult
import com.eu.simpleloginlogout.core.persistance.IDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * AuthViewModel that manages uiStates for AuthFragment
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: IDataStore
) : ViewModel() {
    private val _uiState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Init)
    val uiState: StateFlow<AuthState> get() = _uiState

    private val _navigationEvent = Channel<Boolean>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun dispatchIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Submit -> {
                viewModelScope.launch {
                    authRepository.authUser(intent.username, intent.password).collect {
                        _uiState.value = when (it) {
                            NetworkResult.OnLoading -> AuthState.Loading
                            is NetworkResult.OnNotAuthorized -> AuthState.Error(it.messageId)
                            is NetworkResult.OnSuccess -> AuthState.Success(it.data)
                            is NetworkResult.OnUnexpected -> AuthState.Error(it.messageId)
                        }
                    }
                }
            }
            is AuthIntent.SaveUserToDataStore -> {
                viewModelScope.launch {
                    dataStore.saveUserToPreferencesStore(intent.data)
                    _navigationEvent.send(true)
                }
            }
        }
    }
}
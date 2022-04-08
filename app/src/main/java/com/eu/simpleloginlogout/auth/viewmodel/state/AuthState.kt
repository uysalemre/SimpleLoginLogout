package com.eu.simpleloginlogout.auth.viewmodel.state

import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel

/**
 * @author Emre UYSAL
 * Auth state that contains ui states for AuthFragment¬
 */
sealed class AuthState {
    object Init : AuthState()
    object Loading : AuthState()
    data class Error(val message: Int) : AuthState()
    data class Success(val result: AuthResponseModel) : AuthState()
}
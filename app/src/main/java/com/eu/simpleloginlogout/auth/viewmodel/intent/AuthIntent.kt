package com.eu.simpleloginlogout.auth.viewmodel.intent

import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel

/**
 * @author Emre UYSAL
 * AuthIntent that contains user interaction intents for AuthFragment
 */
sealed class AuthIntent {
    data class Submit(val username: String, val password: String) : AuthIntent()

    data class SaveUserToDataStore(val data: AuthResponseModel) : AuthIntent()
}

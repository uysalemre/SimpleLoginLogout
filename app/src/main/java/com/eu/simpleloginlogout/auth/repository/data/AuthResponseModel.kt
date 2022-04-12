package com.eu.simpleloginlogout.auth.repository.data

/**
 * @author Emre UYSAL
 * AuthResponseModel for login response
 */
data class AuthResponseModel(val token: String?, val refreshToken: String?)

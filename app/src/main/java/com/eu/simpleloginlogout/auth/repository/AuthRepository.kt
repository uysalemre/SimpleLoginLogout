package com.eu.simpleloginlogout.auth.repository

import com.eu.simpleloginlogout.auth.repository.data.AuthRequestModel
import com.eu.simpleloginlogout.auth.repository.service.AuthServices
import com.eu.simpleloginlogout.core.network.NetworkResult
import com.eu.simpleloginlogout.core.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * AuthRepository that creates network call for login request
 */
class AuthRepository @Inject constructor(private val services: AuthServices) {

    fun authUser(username: String, password: String) = flow {
        emit(NetworkResult.OnLoading)
        emit(safeApiCall { services.authUser(AuthRequestModel(username, password)) })
    }.flowOn(Dispatchers.IO)
}
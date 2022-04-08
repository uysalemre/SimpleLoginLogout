package com.eu.simpleloginlogout.profile.repository

import com.eu.simpleloginlogout.core.network.NetworkResult
import com.eu.simpleloginlogout.core.network.safeApiCall
import com.eu.simpleloginlogout.profile.repository.service.ProfileServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * Profile repository that contains api call for user
 */
class ProfileRepository @Inject constructor(private val services: ProfileServices) {
    fun getUser() = flow {
        emit(NetworkResult.OnLoading)
        emit(safeApiCall { services.getProfile() })
    }.flowOn(Dispatchers.IO)
}
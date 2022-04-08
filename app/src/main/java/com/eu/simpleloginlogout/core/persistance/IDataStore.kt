package com.eu.simpleloginlogout.core.persistance

import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Emre UYSAL
 * DataStore interface
 */
interface IDataStore {

    suspend fun saveUserToPreferencesStore(authResponseModel: AuthResponseModel)

    fun getUserFromPreferencesStore(): Flow<AuthResponseModel>
}
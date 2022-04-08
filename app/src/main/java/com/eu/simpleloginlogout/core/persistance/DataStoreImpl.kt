package com.eu.simpleloginlogout.core.persistance

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * DataStore implementation class
 */
class DataStoreImpl @Inject constructor(private val userDataStore: DataStore<Preferences>) :
    IDataStore {

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveUserToPreferencesStore(authResponseModel: AuthResponseModel) {
        userDataStore.edit {
            it[TOKEN] = authResponseModel.token
            it[REFRESH_TOKEN] = authResponseModel.refreshToken
        }
    }

    override fun getUserFromPreferencesStore(): Flow<AuthResponseModel> {
        return userDataStore.data.map {
            AuthResponseModel(it[TOKEN] ?: "", it[REFRESH_TOKEN] ?: "")
        }
    }
}
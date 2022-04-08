package com.eu.simpleloginlogout.core.network

import com.eu.simpleloginlogout.core.persistance.IDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * TokenAuthenticator is written for network requests where access token expired
 * It adds refresh token to header
 * Out of scope
 */
class TokenAuthenticator @Inject constructor(private val dataStore: IDataStore) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken =
            runBlocking { dataStore.getUserFromPreferencesStore().first().refreshToken }
        return response.request.newBuilder().header("authorization", refreshToken).build()
    }
}
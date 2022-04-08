package com.eu.simpleloginlogout.core.network

import com.eu.simpleloginlogout.core.persistance.IDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * Authorization interceptor that adds token to user request
 */
class AuthorizationInterceptor @Inject constructor(private val dataStore: IDataStore) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (chain.request().url.pathSegments.contains("user")) {
            chain.request().newBuilder()
                .addHeader(
                    "authorization",
                    runBlocking { dataStore.getUserFromPreferencesStore().first().token })
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
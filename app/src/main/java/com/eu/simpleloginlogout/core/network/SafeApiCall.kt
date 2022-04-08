package com.eu.simpleloginlogout.core.network

import com.eu.simpleloginlogout.R
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        NetworkResult.OnSuccess(apiCall())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> NetworkResult.OnUnexpected(
                R.string.err_http_internet
            )
            is HttpException -> {
                when (throwable.code()) {
                    401 -> NetworkResult.OnNotAuthorized(
                        R.string.err_http_auth
                    )
                    else -> NetworkResult.OnUnexpected(
                        R.string.err_http_unknown
                    )
                }
            }
            else -> NetworkResult.OnUnexpected(R.string.err_http_unknown)
        }
    }
}
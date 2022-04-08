package com.eu.simpleloginlogout.auth.repository.service

import com.eu.simpleloginlogout.auth.repository.data.AuthRequestModel
import com.eu.simpleloginlogout.auth.repository.data.AuthResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Emre UYSAL
 * AuthServices makes the login request
 */
interface AuthServices {

    @POST("credentials")
    suspend fun authUser(@Body auth: AuthRequestModel): AuthResponseModel
}
package com.eu.simpleloginlogout.profile.repository.service

import com.eu.simpleloginlogout.profile.repository.data.ProfileResponseModel
import retrofit2.http.GET

/**
 * @author Emre UYSAL
 * user information service
 */
interface ProfileServices {

    @GET("user")
    suspend fun getProfile(): ProfileResponseModel
}
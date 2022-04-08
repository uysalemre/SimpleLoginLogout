package com.eu.simpleloginlogout.profile.repository.data

/**
 * @author Emre UYSAL
 * User information response model
 */
data class ProfileResponseModel(
    val address: String,
    val firstName: String,
    val image: String,
    val lastName: String,
    val phone: String,
    val uuid: String
)
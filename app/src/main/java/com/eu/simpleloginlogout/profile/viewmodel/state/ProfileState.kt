package com.eu.simpleloginlogout.profile.viewmodel.state

import com.eu.simpleloginlogout.profile.repository.data.ProfileResponseModel

/**
 * @author Emre UYSAL
 * ProfileState that contains ui states for ProfileFragment
 */
sealed class ProfileState {
    object Init : ProfileState()
    object Loading : ProfileState()
    object UnAuthorizedAccess : ProfileState()
    object Logout : ProfileState()
    data class Error(val message: Int) : ProfileState()
    data class Success(val result: ProfileResponseModel) : ProfileState()
}

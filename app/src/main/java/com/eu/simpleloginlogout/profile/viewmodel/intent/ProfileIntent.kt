package com.eu.simpleloginlogout.profile.viewmodel.intent

/**
 * @author Emre UYSAL
 * ProfileIntent that defines user interactions with intent
 */
sealed class ProfileIntent {
    object GetUser : ProfileIntent()

    object SwipeAndRefresh : ProfileIntent()

    object LogoutUser : ProfileIntent()
}

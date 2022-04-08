package com.eu.simpleloginlogout.core.di

import com.eu.simpleloginlogout.auth.repository.AuthRepository
import com.eu.simpleloginlogout.auth.repository.service.AuthServices
import com.eu.simpleloginlogout.profile.repository.ProfileRepository
import com.eu.simpleloginlogout.profile.repository.service.ProfileServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

/**
 * @author Emre UYSAL
 * Activity scoped module
 * provides repositories
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @Singleton
    fun provideAuthRepository(services: AuthServices) = AuthRepository(services)

    @Provides
    @Singleton
    fun provideProfileRepository(services: ProfileServices) = ProfileRepository(services)
}
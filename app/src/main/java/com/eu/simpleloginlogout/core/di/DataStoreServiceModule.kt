package com.eu.simpleloginlogout.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Emre UYSAL
 * provides data store instance
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreServiceModule {

    private val Context.userPreferencesDataStore by preferencesDataStore(
        name = "user"
    )

    @Singleton
    @Provides
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userPreferencesDataStore
}
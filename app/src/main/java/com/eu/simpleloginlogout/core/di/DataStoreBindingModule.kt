package com.eu.simpleloginlogout.core.di

import com.eu.simpleloginlogout.core.persistance.DataStoreImpl
import com.eu.simpleloginlogout.core.persistance.IDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Emre UYSAL
 * Binds DataStore interface
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreBindingModule {

    @Singleton
    @Binds
    abstract fun bindDataStoreService(impl: DataStoreImpl): IDataStore
}
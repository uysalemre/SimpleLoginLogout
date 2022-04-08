package com.eu.simpleloginlogout.core.di

import com.eu.simpleloginlogout.BuildConfig
import com.eu.simpleloginlogout.auth.repository.service.AuthServices
import com.eu.simpleloginlogout.core.network.AuthorizationInterceptor
import com.eu.simpleloginlogout.core.network.TokenAuthenticator
import com.eu.simpleloginlogout.core.persistance.IDataStore
import com.eu.simpleloginlogout.profile.repository.service.ProfileServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Emre UYSAL
 * App module
 * provides network related instances
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://vidqjclbhmef.herokuapp.com/"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(dataStore: IDataStore) = AuthorizationInterceptor(dataStore)

    @Provides
    @Singleton
    fun provideTokenAuthorization(dataStore: IDataStore) = TokenAuthenticator(dataStore)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ) =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)

    @Singleton
    @Provides
    fun provideProfileServices(retrofit: Retrofit): ProfileServices =
        retrofit.create(ProfileServices::class.java)
}
package com.softbinator.di

import android.content.Context
import com.softbinator.BASE_URL
import com.softbinator.data.framework.AnimalRepositoryImpl
import com.softbinator.data.network.OAuthApi
import com.softbinator.data.network.PetFinderApi
import com.softbinator.data.network.SessionManager
import com.softbinator.data.network.TokenAuthenticator
import com.softbinator.domain.repository.AnimalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesOuthApi(): OAuthApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(OAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPetFinderApi(tokenAuthenticator: TokenAuthenticator): PetFinderApi {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .addInterceptor(interceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return retrofit.create(PetFinderApi::class.java)
    }

    @Provides
    fun providesTokenAuthenticator(
        oAuthApi: OAuthApi,
        sessionManager: SessionManager
    ): TokenAuthenticator {
        return TokenAuthenticator(oAuthApi, sessionManager)
    }

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext appContext: Context
    ): SessionManager {
        return SessionManager(appContext)
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class AnimalRepositoryModule {
    @Binds
    abstract fun bindNavigator(impl: AnimalRepositoryImpl): AnimalRepository
}
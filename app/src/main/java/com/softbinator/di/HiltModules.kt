package com.softbinator.di

import com.softbinator.data.AnimalRepository
import com.softbinator.domain.framework.AnimalRepositoryImpl
import com.softbinator.BASE_URL
import com.softbinator.network.AuthInterceptor
import com.softbinator.network.OAuthApi
import com.softbinator.network.PetFinderApi
import com.softbinator.network.SessionManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesPetFinderApi(authInterceptor: AuthInterceptor): PetFinderApi {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
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
    fun providesAuthInterceptor(
        oAuthApi: OAuthApi,
        sessionManager: SessionManager
    ): AuthInterceptor {
        return AuthInterceptor(oAuthApi, sessionManager);
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class AnimalRepositoryModule {
    @Binds
    abstract fun bindNavigator(impl: AnimalRepositoryImpl): AnimalRepository
}
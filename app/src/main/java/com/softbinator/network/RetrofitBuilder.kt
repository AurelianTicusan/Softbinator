package com.softbinator.network

import com.softbinator.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val retrofit1: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    private val oAuthApi: OAuthApi = retrofit1.create(OAuthApi::class.java)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(AuthInterceptor(oAuthApi))
        .build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    val petFinderApi: PetFinderApi = retrofit.create(PetFinderApi::class.java)

}
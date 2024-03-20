package com.softbinator.network

import com.softbinator.network.data.AnimalResponse
import com.softbinator.network.data.AnimalsResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PetFinderApi {
    @GET("animals")
    suspend fun getAnimals(@Query("page") page: Int = 1): AnimalsResponse

    @GET("animals/{id}")
    fun getAnimal(@Path("id") id: Int): Single<AnimalResponse>
}
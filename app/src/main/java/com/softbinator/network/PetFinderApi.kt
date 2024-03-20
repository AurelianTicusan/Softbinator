package com.softbinator.network

import com.softbinator.network.data.AnimalsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PetFinderApi {
    @GET("animals")
    suspend fun getAnimals(@Query("page") page: Int = 1): AnimalsResponse

    @GET("animal")
    suspend fun getAnimal(id: Int)
}
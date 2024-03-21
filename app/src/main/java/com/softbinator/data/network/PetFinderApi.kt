package com.softbinator.data.network

import com.softbinator.data.network.response.AnimalResponse
import com.softbinator.data.network.response.AnimalsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PetFinderApi {
    @GET("animals")
    suspend fun getAnimals(@Query("page") page: Int = 1): AnimalsResponse

    @GET("animals/{id}")
    fun getAnimal(@Path("id") id: Int): Single<AnimalResponse>
}
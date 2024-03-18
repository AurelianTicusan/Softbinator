package com.softbinator

import com.softbinator.network.PetFinderApi

class AnimalRemoteDataSource(private val petFinderApi: PetFinderApi) {

    suspend fun getAnimals(page: Int) = petFinderApi.getAnimals()

}
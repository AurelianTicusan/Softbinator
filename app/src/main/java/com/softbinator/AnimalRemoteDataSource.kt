package com.softbinator

import com.softbinator.data.network.PetFinderApi
import javax.inject.Inject

class AnimalRemoteDataSource @Inject constructor(private val petFinderApi: PetFinderApi) {

    suspend fun getAnimals(page: Int) = petFinderApi.getAnimals(page)
    fun getAnimal(id: Int) = petFinderApi.getAnimal(id)

}
package com.softbinator

import androidx.paging.PagingData
import com.softbinator.network.data.Animal
import kotlinx.coroutines.flow.Flow

class GetAnimalsUseCase constructor(private val repository: AnimalRepository) {
    suspend fun execute(): Flow<PagingData<Animal>> {
        return repository.getAnimals()
    }
}
package com.softbinator.domain.usecase

import androidx.paging.PagingData
import com.softbinator.domain.model.Animal
import com.softbinator.domain.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(private val repository: AnimalRepository) {
    suspend fun execute(): Flow<PagingData<Animal>> {
        return repository.getAnimals()
    }
}
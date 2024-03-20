package com.softbinator.domain.usecase

import com.softbinator.data.AnimalRepository
import com.softbinator.network.data.Animal
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimalByIdUseCase @Inject constructor(private val repository: AnimalRepository) {
    fun execute(id: Int): Single<Animal> {
        return repository.getAnimal(id)
    }
}
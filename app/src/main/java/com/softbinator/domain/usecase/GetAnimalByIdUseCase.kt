package com.softbinator.domain.usecase

import com.softbinator.data.AnimalRepository
import com.softbinator.domain.model.Animal
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAnimalByIdUseCase @Inject constructor(private val repository: AnimalRepository) {
    fun execute(id: Int): Single<Animal> {
        return repository.getAnimal(id)
    }
}
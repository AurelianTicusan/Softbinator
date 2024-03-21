package com.softbinator.data

import androidx.paging.PagingData
import com.softbinator.domain.model.Animal
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    suspend fun getAnimals(): Flow<PagingData<Animal>>
    fun getAnimal(id: Int): Single<Animal>
}
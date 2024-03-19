package com.softbinator.data

import androidx.paging.PagingData
import com.softbinator.network.data.Animal
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    suspend fun getAnimals(): Flow<PagingData<Animal>>
}
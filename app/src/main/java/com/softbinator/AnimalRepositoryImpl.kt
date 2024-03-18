package com.softbinator

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.softbinator.network.data.Animal
import kotlinx.coroutines.flow.Flow

class AnimalRepositoryImpl(
    private val remoteDataSource: AnimalRemoteDataSource
) : AnimalRepository {
    override suspend fun getAnimals(): Flow<PagingData<Animal>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                AnimalPagingSource(remoteDataSource)
            }
        ).flow
    }
}
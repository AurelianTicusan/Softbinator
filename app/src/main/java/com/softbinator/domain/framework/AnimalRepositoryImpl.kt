package com.softbinator.domain.framework

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.softbinator.AnimalPagingSource
import com.softbinator.AnimalRemoteDataSource
import com.softbinator.MAX_PAGE_SIZE
import com.softbinator.data.AnimalRepository
import com.softbinator.network.data.Animal
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimalRepositoryImpl @Inject constructor(
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

    override fun getAnimal(id: Int): Single<Animal> {
        return remoteDataSource.getAnimal(id)
            .map { it.animal }
    }
}
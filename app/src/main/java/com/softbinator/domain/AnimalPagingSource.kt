package com.softbinator.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softbinator.AnimalRemoteDataSource
import com.softbinator.data.mapper.toAnimal
import com.softbinator.domain.model.Animal
import retrofit2.HttpException
import java.io.IOException

class AnimalPagingSource(
    private val remoteDataSource: AnimalRemoteDataSource,
) : PagingSource<Int, Animal>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Animal> {
        return try {
            val currentPage = params.key ?: 1
            val animals = remoteDataSource.getAnimals(
                page = currentPage
            )
            LoadResult.Page(
                data = animals.animals.map { it.toAnimal() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (animals.animals.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Animal>): Int? {
        return state.anchorPosition
    }

}
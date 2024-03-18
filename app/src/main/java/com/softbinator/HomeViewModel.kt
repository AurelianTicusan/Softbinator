package com.softbinator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softbinator.network.data.Animal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Animal>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<Animal>> get() = _moviesState

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getAnimals()
                }
            }
        }
    }

    private suspend fun getAnimals() {
        getAnimalsUseCase.execute()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }
}

sealed class HomeEvent {
    data object GetHome : HomeEvent()
}
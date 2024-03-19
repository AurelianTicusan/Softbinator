package com.softbinator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softbinator.domain.usecase.GetAnimalsUseCase
import com.softbinator.network.data.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    private val _animalsState: MutableStateFlow<PagingData<Animal>> =
        MutableStateFlow(value = PagingData.empty())
    val animalsState: MutableStateFlow<PagingData<Animal>> get() = _animalsState

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
            .collect { _animalsState.value = it }
    }
}

sealed class HomeEvent {
    data object GetHome : HomeEvent()
}
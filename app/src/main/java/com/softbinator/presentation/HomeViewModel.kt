package com.softbinator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.softbinator.domain.usecase.GetAnimalsUseCase
import com.softbinator.presentation.mapper.toAnimalItem
import com.softbinator.presentation.state.AnimalItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    private val _animalsState: MutableStateFlow<PagingData<AnimalItem>> =
        MutableStateFlow(value = PagingData.empty())
    val animalsState: MutableStateFlow<PagingData<AnimalItem>> get() = _animalsState

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
            .collect { _animalsState.value = it.map { item -> item.toAnimalItem() } }
    }
}

sealed class HomeEvent {
    data object GetHome : HomeEvent()
}
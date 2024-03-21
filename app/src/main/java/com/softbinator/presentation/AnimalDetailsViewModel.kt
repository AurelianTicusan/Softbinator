package com.softbinator.presentation

import androidx.lifecycle.ViewModel
import com.softbinator.domain.usecase.GetAnimalByIdUseCase
import com.softbinator.presentation.mapper.toAnimalItem
import com.softbinator.presentation.state.AnimalItem
import com.softbinator.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val useCase: GetAnimalByIdUseCase
) : ViewModel() {

    private val _animalsState: MutableStateFlow<UiState<AnimalItem>> =
        MutableStateFlow(value = UiState.Loading())
    val animalsState: StateFlow<UiState<AnimalItem>> get() = _animalsState.asStateFlow()

    private var animalDetailsDisposable: Disposable? = null

    fun initialize(id: Int) {
        animalDetailsDisposable = getAnimal(id)
    }

    private fun getAnimal(id: Int) = useCase.execute(id)
        .subscribeBy(
            onSuccess = { _animalsState.value = UiState.Loaded(it.toAnimalItem()) },
            onError = { _animalsState.value = UiState.Error(it.localizedMessage) }
        )

    override fun onCleared() {
        animalDetailsDisposable?.apply { if (isDisposed.not()) dispose() }
        super.onCleared()
    }
}
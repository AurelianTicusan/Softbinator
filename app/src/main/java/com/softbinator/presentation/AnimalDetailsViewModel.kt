package com.softbinator.presentation

import androidx.lifecycle.ViewModel
import com.softbinator.domain.usecase.GetAnimalByIdUseCase
import com.softbinator.network.data.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val useCase: GetAnimalByIdUseCase
) : ViewModel() {

    private val _animalsState: MutableStateFlow<Animal> =
        MutableStateFlow(value = Animal())
    val animalsState: MutableStateFlow<Animal> get() = _animalsState

    private var animalDetailsDisposable: Disposable? = null

    fun initialize(id: Int) {
        animalDetailsDisposable = getAnimal(id)
    }

    private fun getAnimal(id: Int) = useCase.execute(id)
        .subscribeBy(
            onSuccess = { _animalsState.value = it },
            onError = {}
        )

    override fun onCleared() {
        animalDetailsDisposable?.apply { if (isDisposed.not()) dispose() }
        super.onCleared()
    }
}
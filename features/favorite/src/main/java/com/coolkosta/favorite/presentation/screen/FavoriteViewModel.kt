package com.coolkosta.favorite.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.core.domain.model.VacancyEntity
import com.coolkosta.core.util.CoroutineExceptionHandler
import com.coolkosta.core.util.VacancyFlow
import com.coolkosta.favorite.domain.interactor.VacancyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {
    private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val state = _state.asStateFlow()

    init {
        getFavoriteVacancies()
    }

    fun sendEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.VacancyFavoriteItemsChanged -> {
                viewModelScope.launch {
                    updateFavoriteVacancy(event.vacancy)
                    val newVacancies = vacancyInteractor.getVacancyList()
                    val counter = newVacancies.filter { item -> item.isFavorite }.size
                    fetchFavoriteCount(counter)
                    _state.update { FavoriteState.Success(newVacancies) }
                }
            }
        }
    }

    private fun getFavoriteVacancies() {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler.create("FavoriteViewModel") { ex ->
                _state.update { FavoriteState.Error }
            }
        viewModelScope.launch(coroutineExceptionHandler) {
            val vacancies = vacancyInteractor.getVacancyList()
            _state.update { FavoriteState.Success(vacancies) }
        }
    }

    private suspend fun updateFavoriteVacancy(vacancy: VacancyEntity) {
        vacancyInteractor.updateFavoriteVacancy(vacancy)

    }

    private fun fetchFavoriteCount(favoriteCount: Int) {
        VacancyFlow.publish(favoriteCount)
    }
}
package com.coolkosta.search.presentation.screen.searchFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.core.util.CoroutineExceptionHandler
import com.coolkosta.core.util.VacancyFlow
import com.coolkosta.search.domain.interactor.OfferInteractor
import com.coolkosta.search.domain.interactor.VacancyInteractor
import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.core.domain.model.VacanciesCount
import com.coolkosta.core.domain.model.VacancyEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val offerInteractor: OfferInteractor,
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    private fun updateState(offers: List<OfferEntity>, vacancies: List<VacancyEntity>) {
        _state.update { SearchState.Success(offers, vacancies, VacanciesCount(vacancies.size)) }
        val counter = vacancies.filter { item -> item.isFavorite }.size
        fetchFavoriteCount(counter)
    }

    private fun getData() {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler.create("SearchViewModel") { ex ->
                _state.update { SearchState.Error }
            }
        viewModelScope.launch(coroutineExceptionHandler) {
            val offersDeferred = async {
                offerInteractor.getOfferList()
            }
            val vacanciesDeferred = async {
                vacancyInteractor.getVacancyList()
            }
            val offers = offersDeferred.await()
            val vacancies = vacanciesDeferred.await()
            updateState(offers, vacancies)
        }
    }

    fun sendEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.VacancyListOpened -> {
                changeVacancyList(searchEvent.isListFull)
            }

            is SearchEvent.VacancyFavoriteItemsChanged -> {
                val current = _state.value as SearchState.Success
                viewModelScope.launch {
                    changeFavoriteItem(searchEvent.vacancy)
                    val newVacancies = vacancyInteractor.getVacancyListFromDb()
                    val counter = newVacancies.filter { item -> item.isFavorite }.size
                    fetchFavoriteCount(counter)
                    _state.update { current.copy(vacancies = newVacancies) }
                }

            }
        }
    }

    private fun changeVacancyList(isListFull: Boolean) {
        val current = _state.value as SearchState.Success
        _state.update { current.copy(isListFull = isListFull) }

    }

    private suspend fun changeFavoriteItem(vacancy: VacancyEntity) {
        vacancyInteractor.updateFavoriteVacancy(vacancy)
    }

    private fun fetchFavoriteCount(favoriteCount: Int) {
        VacancyFlow.publish(favoriteCount)
    }

}
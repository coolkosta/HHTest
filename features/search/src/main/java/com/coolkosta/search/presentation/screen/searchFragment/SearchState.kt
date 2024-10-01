package com.coolkosta.search.presentation.screen.searchFragment

import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.core.domain.model.VacanciesCount
import com.coolkosta.core.domain.model.VacancyEntity

sealed interface SearchState {
    data object Loading : SearchState

    data class Success(
        val offers: List<OfferEntity>,
        val vacancies: List<VacancyEntity>,
        val vacanciesCount: VacanciesCount,
        val isListFull: Boolean = false
    ) : SearchState

    data object Error : SearchState
}

package com.coolkosta.search.presentation.screen.searchFragment

import com.coolkosta.core.domain.model.VacancyEntity


sealed interface SearchEvent {
    data class VacancyListOpened(val isListFull: Boolean) : SearchEvent
    data class VacancyFavoriteItemsChanged(val vacancy: VacancyEntity) : SearchEvent
}
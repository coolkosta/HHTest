package com.coolkosta.favorite.presentation.screen

import com.coolkosta.core.domain.model.VacancyEntity

sealed interface FavoriteState {
    object Loading : FavoriteState
    data class Success(val vacancies: List<VacancyEntity>) : FavoriteState
    object Error : FavoriteState
}
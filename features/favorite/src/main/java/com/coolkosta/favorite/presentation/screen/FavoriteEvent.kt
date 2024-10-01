package com.coolkosta.favorite.presentation.screen

import com.coolkosta.core.domain.model.VacancyEntity

sealed class FavoriteEvent {
    data class VacancyFavoriteItemsChanged(val vacancy: VacancyEntity) : FavoriteEvent()
}
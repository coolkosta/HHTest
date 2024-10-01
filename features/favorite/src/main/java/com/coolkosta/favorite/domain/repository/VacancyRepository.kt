package com.coolkosta.favorite.domain.repository

import com.coolkosta.core.domain.model.VacancyEntity

interface VacancyRepository {
    suspend fun getFavoriteVacancies(): List<VacancyEntity>
    suspend fun updateFavoriteVacancy(vacancy: VacancyEntity)
}
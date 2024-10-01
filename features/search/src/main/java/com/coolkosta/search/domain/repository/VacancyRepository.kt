package com.coolkosta.search.domain.repository

import com.coolkosta.core.domain.model.VacancyEntity

interface VacancyRepository {
    suspend fun getVacancies(): List<VacancyEntity>
    suspend fun updateFavoriteVacancy(vacancy: VacancyEntity)
    suspend fun getLocalVacancies(): List<VacancyEntity>
}
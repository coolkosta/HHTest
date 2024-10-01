package com.coolkosta.search.domain.repository

import com.coolkosta.search.domain.model.VacancyEntity

fun interface VacancyRepository {
    suspend fun getVacancies(): List<VacancyEntity>
}
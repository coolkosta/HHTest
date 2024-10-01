package com.coolkosta.favorite.domain.interactor

import com.coolkosta.core.domain.model.VacancyEntity
import com.coolkosta.favorite.domain.repository.VacancyRepository
import javax.inject.Inject

class VacancyInteractor @Inject constructor(private val repository: VacancyRepository) {
    suspend fun getVacancyList(): List<VacancyEntity> {
        return repository.getFavoriteVacancies()
    }

    suspend fun updateFavoriteVacancy(vacancy: VacancyEntity) {
        repository.updateFavoriteVacancy(vacancy)
    }
}
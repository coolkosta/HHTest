package com.coolkosta.search.domain.interactor

import com.coolkosta.search.domain.model.VacancyEntity
import com.coolkosta.search.domain.repository.VacancyRepository
import javax.inject.Inject

class VacancyInteractor @Inject constructor(
    private val vacancyRepository: VacancyRepository
) {
    suspend fun getVacancyList(): List<VacancyEntity> {
        return vacancyRepository.getVacancies()
    }
}
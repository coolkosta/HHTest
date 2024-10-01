package com.coolkosta.favorite.data.repository

import android.util.Log
import com.coolkosta.core.data.mapper.VacancyMapper
import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.core.domain.model.VacancyEntity
import com.coolkosta.favorite.domain.repository.VacancyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val vacancyDao: VacancyDao,
    private val dispatcher: CoroutineDispatcher
) : VacancyRepository {
    override suspend fun getFavoriteVacancies(): List<VacancyEntity> {
        val vacancies = withContext(dispatcher) {
            runCatching {
                vacancyDao.getFavoriteVacancies()
                    .map { VacancyMapper.fromVacancyDbModelToVacancyEntity(it) }
            }.getOrElse { ex ->
                Log.e(
                    VacancyRepositoryImpl::class.simpleName,
                    "Can't get vacancy from db: ${ex.message}"
                )
                emptyList()
            }
        }
        return vacancies
    }

    override suspend fun updateFavoriteVacancy(vacancy: VacancyEntity) {
        withContext(dispatcher) {
            vacancyDao.updateVacancy(VacancyMapper.fromVacancyEntityToVacancyDbModel(vacancy))
        }
    }

}
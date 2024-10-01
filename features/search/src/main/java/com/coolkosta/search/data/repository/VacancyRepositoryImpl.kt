package com.coolkosta.search.data.repository

import android.util.Log
import com.coolkosta.core.data.mapper.VacancyMapper
import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.core.domain.model.VacancyEntity
import com.coolkosta.search.data.mapper.VacancyResponseMapper
import com.coolkosta.search.data.source.remote.api.ApiService
import com.coolkosta.search.domain.repository.VacancyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val vacancyDao: VacancyDao,
    private val dispatcher: CoroutineDispatcher
) : VacancyRepository {
    override suspend fun getVacancies(): List<VacancyEntity> {
        val vacancyList = withContext(dispatcher) {
            runCatching {
                fetchAndInsertVacancies()
            }.getOrElse { ex ->
                Log.e(
                    VacancyRepositoryImpl::class.simpleName,
                    "Can't get remote vacancy data: ${ex.message}"
                )
                getVacanciesFromDb()
            }
        }
        check(vacancyList.isNotEmpty())
        return vacancyList
    }

    override suspend fun updateFavoriteVacancy(vacancy: VacancyEntity) {
        withContext(dispatcher) {
            vacancyDao.updateVacancy(VacancyMapper.fromVacancyEntityToVacancyDbModel(vacancy))
        }
    }

    override suspend fun getLocalVacancies(): List<VacancyEntity> {
        val list = withContext(dispatcher) {
            runCatching {
                getVacanciesFromDb()
            }.getOrElse { ex ->
                Log.e(
                    VacancyRepositoryImpl::class.simpleName,
                    "Can't get vacancy from db: ${ex.message}"
                )
                emptyList()
            }
        }
        check(list.isNotEmpty())
        return list
    }

    private suspend fun fetchAndInsertVacancies(): List<VacancyEntity> {
        val vacancyDbList =
            api.getVacancies().map { VacancyResponseMapper.fromVacancyResponseToVacancyDbModel(it) }
        vacancyDao.insertVacancies(vacancyDbList)
        return vacancyDbList.map { VacancyMapper.fromVacancyDbModelToVacancyEntity(it) }
    }

    private suspend fun getVacanciesFromDb(): List<VacancyEntity> {
        return vacancyDao.getAllVacancies()
            .map { VacancyMapper.fromVacancyDbModelToVacancyEntity(it) }
    }
}
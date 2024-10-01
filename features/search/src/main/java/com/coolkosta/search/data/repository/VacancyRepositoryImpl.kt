package com.coolkosta.search.data.repository

import android.util.Log
import com.coolkosta.search.data.mapper.VacancyMapper
import com.coolkosta.search.data.source.remote.api.ApiService
import com.coolkosta.search.domain.model.VacancyEntity
import com.coolkosta.search.domain.repository.VacancyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher
) : VacancyRepository {
    override suspend fun getVacancies(): List<VacancyEntity> {
        val vacancyList = withContext(dispatcher) {
            runCatching {
                fetchVacancies()
            }.getOrElse { ex ->
                Log.e(
                    VacancyRepositoryImpl::class.simpleName,
                    "Can't get remote vacancy data: ${ex.message}"
                )
                emptyList()
            }
        }
        check(vacancyList.isNotEmpty())
        return vacancyList
    }

    private suspend fun fetchVacancies(): List<VacancyEntity> {
        val vacancyList =
            api.getVacancies().map { VacancyMapper.fromVacancyResponseToVacancyEntity(it) }
        return vacancyList
    }
}
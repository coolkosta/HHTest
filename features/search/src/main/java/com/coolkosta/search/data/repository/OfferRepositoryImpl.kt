package com.coolkosta.search.data.repository

import android.util.Log
import com.coolkosta.search.data.mapper.OfferMapper
import com.coolkosta.search.data.source.remote.api.ApiService
import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.search.domain.repository.OfferRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher
) : OfferRepository {
    override suspend fun getOffers(): List<OfferEntity> {
        val offerList = withContext(dispatcher) {
            runCatching {
                fetchOffers()
            }.getOrElse { ex ->
                Log.e(
                    OfferRepositoryImpl::class.simpleName,
                    "Can't get remote offer data: ${ex.message}"
                )
                emptyList()
            }
        }
        check(offerList.isNotEmpty())
        return offerList
    }

    private suspend fun fetchOffers(): List<OfferEntity> {
        val offerList = api.getOffers().map { OfferMapper.fromOfferResponseToOfferEntity(it) }
        return offerList
    }
}
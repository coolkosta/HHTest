package com.coolkosta.search.domain.interactor

import com.coolkosta.search.domain.model.OfferEntity
import com.coolkosta.search.domain.repository.OfferRepository
import javax.inject.Inject

class OfferInteractor @Inject constructor(
    private val offerRepository: OfferRepository
) {
    suspend fun getOfferList(): List<OfferEntity> {
        return offerRepository.getOffers()
    }
}
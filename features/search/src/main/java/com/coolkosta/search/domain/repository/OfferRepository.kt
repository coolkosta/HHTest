package com.coolkosta.search.domain.repository

import com.coolkosta.search.domain.model.OfferEntity

fun interface OfferRepository {
    suspend fun getOffers(): List<OfferEntity>
}
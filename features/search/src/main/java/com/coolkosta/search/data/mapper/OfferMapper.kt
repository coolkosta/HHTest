package com.coolkosta.search.data.mapper

import com.coolkosta.search.data.source.remote.model.OfferResponse
import com.coolkosta.search.domain.model.OfferEntity

object OfferMapper {
    fun fromOfferResponseToOfferEntity(
        offerResponse: OfferResponse,
    ): OfferEntity {
        return OfferEntity(
            buttonText = offerResponse.button?.text,
            id = offerResponse.id,
            link = offerResponse.link,
            title = offerResponse.title
        )
    }
}
package com.coolkosta.search.data.source.remote.api

import com.coolkosta.search.data.source.remote.model.OfferResponse
import com.coolkosta.search.data.source.remote.model.VacancyResponse
import retrofit2.http.GET

interface ApiService {
    @GET("offers.json")
    suspend fun getOffers(): List<OfferResponse>

    @GET("vacancies.json")
    suspend fun getVacancies(): List<VacancyResponse>
}
package com.coolkosta.search.data.source.remote.model

data class VacancyResponse(
    val address: AddressResponse,
    val appliedNumber: Int,
    val company: String,
    val description: String,
    val experience: ExperienceResponse,
    val id: String,
    val isFavorite: Boolean,
    val lookingNumber: Int,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: SalaryResponse,
    val schedules: List<String>,
    val title: String
)
package com.coolkosta.search.domain.model

data class VacancyEntity(
    val id: String,
    val lookingNumber: Int,
    val isFavorite: Boolean,
    val title: String,
    val city: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
) : VacanciesItems()


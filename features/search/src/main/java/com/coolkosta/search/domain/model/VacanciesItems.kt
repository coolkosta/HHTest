package com.coolkosta.search.domain.model

sealed class VacanciesItems

data class VacanciesCount(
    val count: Int
) : VacanciesItems()

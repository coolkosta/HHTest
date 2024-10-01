package com.coolkosta.core.domain.model

sealed class VacanciesItems

data class VacanciesCount(
    val count: Int
) : VacanciesItems()

package com.coolkosta.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object VacancyFlow {
    private val vacancyFlow = MutableStateFlow(0)

    fun publish(favoriteCount: Int) {
        vacancyFlow.value = favoriteCount
    }

    fun getFavoriteVacancyCount(): Flow<Int> = vacancyFlow
}
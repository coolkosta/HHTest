package com.coolkosta.favorite.di

import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.favorite.data.repository.VacancyRepositoryImpl
import com.coolkosta.favorite.domain.repository.VacancyRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideVacancyRepository(
        vacancyDao: VacancyDao,
        dispatcher: CoroutineDispatcher

    ): VacancyRepository = VacancyRepositoryImpl(vacancyDao, dispatcher)
}
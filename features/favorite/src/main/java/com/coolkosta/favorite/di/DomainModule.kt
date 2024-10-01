package com.coolkosta.favorite.di

import com.coolkosta.favorite.domain.interactor.VacancyInteractor
import com.coolkosta.favorite.domain.repository.VacancyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideVacancyInteractor(vacancyRepository: VacancyRepository): VacancyInteractor {
        return VacancyInteractor(vacancyRepository)

    }
}
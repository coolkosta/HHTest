package com.coolkosta.search.di

import com.coolkosta.search.domain.interactor.OfferInteractor
import com.coolkosta.search.domain.interactor.VacancyInteractor
import com.coolkosta.search.domain.repository.OfferRepository
import com.coolkosta.search.domain.repository.VacancyRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideOfferInteractor(repository: OfferRepository): OfferInteractor {
        return OfferInteractor(offerRepository = repository)
    }

    @Provides
    fun provideVacancyInteractor(repository: VacancyRepository): VacancyInteractor {
        return VacancyInteractor(vacancyRepository = repository)
    }
}
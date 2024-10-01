package com.coolkosta.favorite.di

import com.coolkosta.core.data.source.local.dao.VacancyDao
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class], dependencies = [FavoriteDeps::class])
interface FavoriteComponent {
    fun provideFavoriteViewModelFactory(): FavoriteViewModelFactory
}

interface FavoriteDeps {
    val vacancyDao: VacancyDao
    val backgroundCoroutineDispatcher: CoroutineDispatcher
}
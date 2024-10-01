package com.coolkosta.search.di

import com.coolkosta.core.data.source.local.dao.VacancyDao
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, DomainModule::class],
    dependencies = [SearchDeps::class]
)
interface SearchComponent {
    fun searchViewModelFactory(): SearchViewModelFactory
}

interface SearchDeps {
    val vacancyDao: VacancyDao
    val backgroundCoroutineDispatcher: CoroutineDispatcher
}

package com.coolkosta.search.di

import android.app.Application
import android.content.Context
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
    val backgroundCoroutineDispatcher: CoroutineDispatcher
    val context: Context
    val application: Application
}

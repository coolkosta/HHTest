package com.coolkosta.hhtest.di

import com.coolkosta.core.di.CoreDeps
import com.coolkosta.core.di.CoreDataModule
import com.coolkosta.favorite.di.FavoriteDeps
import com.coolkosta.search.di.SearchDeps
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CoreDataModule::class])
interface AppComponent : CoreDeps, SearchDeps, FavoriteDeps
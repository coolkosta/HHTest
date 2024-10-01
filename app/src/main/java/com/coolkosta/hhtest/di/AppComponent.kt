package com.coolkosta.hhtest.di

import com.coolkosta.search.di.SearchDeps
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: SearchDeps {

}
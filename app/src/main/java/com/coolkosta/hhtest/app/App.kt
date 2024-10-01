package com.coolkosta.hhtest.app

import android.app.Application
import com.coolkosta.hhtest.di.AppComponent
import com.coolkosta.hhtest.di.AppModule
import com.coolkosta.hhtest.di.DaggerAppComponent
import com.coolkosta.search.di.DaggerSearchComponent
import com.coolkosta.search.di.SearchComponent
import com.coolkosta.search.di.SearchComponentProvider

class App : Application(), SearchComponentProvider {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun getSearchComponent(): SearchComponent {
        return DaggerSearchComponent.builder()
            .searchDeps(appComponent)
            .build()
    }

}
package com.coolkosta.hhtest.app

import android.app.Application
import com.coolkosta.core.di.CoreComponent
import com.coolkosta.core.di.CoreComponentProvider
import com.coolkosta.core.di.DaggerCoreComponent
import com.coolkosta.favorite.di.DaggerFavoriteComponent
import com.coolkosta.favorite.di.FavoriteComponent
import com.coolkosta.favorite.di.FavoriteComponentProvider
import com.coolkosta.hhtest.di.AppComponent
import com.coolkosta.hhtest.di.AppModule
import com.coolkosta.hhtest.di.DaggerAppComponent
import com.coolkosta.search.di.DaggerSearchComponent
import com.coolkosta.search.di.SearchComponent
import com.coolkosta.search.di.SearchComponentProvider

class App : Application(), CoreComponentProvider, SearchComponentProvider,
    FavoriteComponentProvider {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun getCoreComponent(): CoreComponent {
        return DaggerCoreComponent.builder()
            .coreDeps(appComponent)
            .build()
    }

    override fun getSearchComponent(): SearchComponent {
        return DaggerSearchComponent.builder()
            .searchDeps(appComponent)
            .build()
    }

    override fun getFavoriteComponent(): FavoriteComponent {
        return DaggerFavoriteComponent.builder()
            .favoriteDeps(appComponent)
            .build()
    }
}
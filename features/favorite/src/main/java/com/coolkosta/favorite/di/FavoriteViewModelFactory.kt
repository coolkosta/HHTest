package com.coolkosta.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.coolkosta.favorite.presentation.screen.FavoriteViewModel
import javax.inject.Inject
import javax.inject.Provider

class FavoriteViewModelFactory @Inject constructor(
    favoriteViewModelProvider: Provider<FavoriteViewModel>
) : ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        FavoriteViewModel::class.java to favoriteViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]!!.get() as T
    }
}
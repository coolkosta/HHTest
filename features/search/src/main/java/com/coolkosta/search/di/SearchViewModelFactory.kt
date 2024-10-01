package com.coolkosta.search.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.coolkosta.search.presentation.screen.searchFragment.SearchViewModel
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModelFactory @Inject constructor(
    searchViewModelProvider: Provider<SearchViewModel>
) : ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        SearchViewModel::class.java to searchViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]!!.get() as T
    }
}
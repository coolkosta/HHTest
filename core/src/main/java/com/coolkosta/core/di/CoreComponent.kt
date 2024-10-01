package com.coolkosta.core.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreDataModule::class], dependencies = [CoreDeps::class]
)
interface CoreComponent

interface CoreDeps {
    val context: Context
}

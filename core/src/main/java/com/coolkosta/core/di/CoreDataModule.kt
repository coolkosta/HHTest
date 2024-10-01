package com.coolkosta.core.di

import android.content.Context
import androidx.room.Room
import com.coolkosta.core.common.Constants.DATABASE_NAME
import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.core.data.source.local.db.VacancyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreDataModule() {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): VacancyDatabase {
        return Room.databaseBuilder(
            context,
            VacancyDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun vacancyDao(database: VacancyDatabase): VacancyDao {
        return database.vacancyDao()
    }
}
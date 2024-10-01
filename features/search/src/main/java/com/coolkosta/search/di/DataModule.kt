package com.coolkosta.search.di

import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.search.data.repository.OfferRepositoryImpl
import com.coolkosta.search.data.repository.VacancyRepositoryImpl
import com.coolkosta.search.data.source.remote.api.ApiService
import com.coolkosta.search.domain.repository.OfferRepository
import com.coolkosta.search.domain.repository.VacancyRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(com.coolkosta.core.common.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideOfferRepository(
        apiService: ApiService,
        dispatcher: CoroutineDispatcher
    ): OfferRepository {
        return OfferRepositoryImpl(
            apiService,
            dispatcher
        )
    }

    @Singleton
    @Provides
    fun provideVacancyRepository(
        apiService: ApiService,
        vacancyDao: VacancyDao,
        dispatcher: CoroutineDispatcher
    ): VacancyRepository {
        return VacancyRepositoryImpl(apiService, vacancyDao, dispatcher)
    }
}
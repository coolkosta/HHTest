package com.coolkosta.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.coolkosta.core.data.source.local.model.VacancyDbModel

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancies(vacancyList: List<VacancyDbModel>)

    @Query("SELECT * FROM vacancy_entity")
    suspend fun getAllVacancies(): List<VacancyDbModel>

    @Update
    suspend fun updateVacancy(vacancy: VacancyDbModel)

    @Query("SELECT * FROM vacancy_entity WHERE is_favorite = 1")
    suspend fun getFavoriteVacancies(): List<VacancyDbModel>
}
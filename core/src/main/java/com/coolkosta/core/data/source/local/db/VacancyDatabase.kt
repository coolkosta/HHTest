package com.coolkosta.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolkosta.core.data.source.local.dao.VacancyDao
import com.coolkosta.core.data.source.local.model.VacancyDbModel

@Database(entities = [VacancyDbModel::class], version = 1)
abstract class VacancyDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao

}
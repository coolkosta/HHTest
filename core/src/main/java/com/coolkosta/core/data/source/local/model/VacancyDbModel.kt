package com.coolkosta.core.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_entity")
data class VacancyDbModel(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "looking_number")
    val lookingNumber: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    val title: String,
    val city: String,
    val company: String,
    val experience: String,
    @ColumnInfo(name = "published_date")
    val publishedDate: String,
)

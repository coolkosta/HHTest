package com.coolkosta.core.data.mapper

import com.coolkosta.core.data.source.local.model.VacancyDbModel
import com.coolkosta.core.domain.model.VacancyEntity

object VacancyMapper {
    fun fromVacancyDbModelToVacancyEntity(vacancyDbModel: VacancyDbModel): VacancyEntity {
        return VacancyEntity(
            id = vacancyDbModel.id,
            lookingNumber = vacancyDbModel.lookingNumber,
            isFavorite = vacancyDbModel.isFavorite,
            title = vacancyDbModel.title,
            city = vacancyDbModel.city,
            company = vacancyDbModel.company,
            experience = vacancyDbModel.experience,
            publishedDate = vacancyDbModel.publishedDate
        )
    }

    fun fromVacancyEntityToVacancyDbModel(vacancyEntity: VacancyEntity): VacancyDbModel {
        return VacancyDbModel(
            id = vacancyEntity.id,
            lookingNumber = vacancyEntity.lookingNumber,
            isFavorite = vacancyEntity.isFavorite,
            title = vacancyEntity.title,
            city = vacancyEntity.city,
            company = vacancyEntity.company,
            experience = vacancyEntity.experience,
            publishedDate = vacancyEntity.publishedDate
        )
    }
}
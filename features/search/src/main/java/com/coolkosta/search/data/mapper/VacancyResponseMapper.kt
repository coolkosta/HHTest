package com.coolkosta.search.data.mapper

import com.coolkosta.core.data.source.local.model.VacancyDbModel
import com.coolkosta.search.data.source.remote.model.VacancyResponse
import kotlinx.datetime.LocalDate


object VacancyResponseMapper {
    fun fromVacancyResponseToVacancyDbModel(vacancyResponse: VacancyResponse): VacancyDbModel {
        return VacancyDbModel(
            id = vacancyResponse.id,
            lookingNumber = vacancyResponse.lookingNumber,
            isFavorite = vacancyResponse.isFavorite,
            title = vacancyResponse.title,
            city = vacancyResponse.address.town,
            company = vacancyResponse.company,
            experience = vacancyResponse.experience.previewText,
            publishedDate = getDatePublishing(vacancyResponse.publishedDate)
        )
    }

    private fun getDatePublishing(dateString: String): String {
        val date = LocalDate.parse(dateString)
        val day = date.dayOfMonth
        val month = date.monthNumber
        val monthFormatter: String = when (month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }
        return "Опубликовано $day $monthFormatter"
    }
}
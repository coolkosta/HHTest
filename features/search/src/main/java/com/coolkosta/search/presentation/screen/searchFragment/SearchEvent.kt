package com.coolkosta.search.presentation.screen.searchFragment


sealed interface SearchEvent {
    data class VacancyListOpened(val isListFull: Boolean) : SearchEvent
}
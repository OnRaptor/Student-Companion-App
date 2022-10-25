package com.example.uksivtcompanion.screens.diary.models

import com.example.uksivtcompanion.data.entities.DiaryItem

sealed class DiaryViewState {
    object Loading : DiaryViewState()
    object Error : DiaryViewState()
    data class Display(
        val items: List<DiaryItem>,
        val title: String,
        val hasNextDay: Boolean
    ) : DiaryViewState()
    object NoItems: DiaryViewState()
}
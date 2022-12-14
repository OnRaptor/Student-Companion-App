package com.example.uksivtcompanion.screens.diary.models

import com.example.uksivtcompanion.data.entities.DiaryItem


sealed class DetailsViewState {
    object Loading : DetailsViewState()
    object Error : DetailsViewState()
    data class Display(
        val diaryItem: DiaryItem
    ) : DetailsViewState()
}
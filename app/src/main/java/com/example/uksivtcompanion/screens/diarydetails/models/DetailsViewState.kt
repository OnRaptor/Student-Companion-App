package com.example.uksivtcompanion.screens.diarydetails.models

import com.example.uksivtcompanion.data.entities.DiaryItem


sealed class DetailsViewState {
    object Loading : DetailsViewState()
    object Error : DetailsViewState()
    data class Display(
        val title: List<DiaryItem>,
        val text: String,
        val date: String,
        val hasNextDay: Boolean
    ) : DetailsViewState()
}
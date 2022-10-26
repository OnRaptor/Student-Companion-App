package com.example.uksivtcompanion.screens.diarydetails.models

import androidx.compose.runtime.MutableState
import com.example.uksivtcompanion.data.entities.DiaryItem


sealed class DetailsViewState {
    object Loading : DetailsViewState()
    object Error : DetailsViewState()
    data class Display(
        val diaryItem: DiaryItem
    ) : DetailsViewState()
}
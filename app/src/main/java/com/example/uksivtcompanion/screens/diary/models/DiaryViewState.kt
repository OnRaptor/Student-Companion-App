package com.example.uksivtcompanion.screens.diary.models

import androidx.compose.runtime.MutableState
import com.example.uksivtcompanion.data.entities.DiaryItem

sealed class DiaryViewState {
    object Loading : DiaryViewState()
    object Error : DiaryViewState()
    data class Display(
        val items: List<DiaryItem>,
        val date: MutableState<String>
    ) : DiaryViewState()
    data class NoItems(
        val date: MutableState<String>
    ) : DiaryViewState()
}
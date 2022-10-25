package com.example.uksivtcompanion.screens.diarydetails.models

import com.example.uksivtcompanion.data.entities.DiaryItem

sealed class DetailsEvent {
    object EnterScreen : DetailsEvent()
    object PreviousDayClicked : DetailsEvent()
    object NextDayClicked : DetailsEvent()
    data class OnSaveClick(val diaryItem: DiaryItem): DetailsEvent()
    data class OnDeleteClick(val diaryUID:String): DetailsEvent()
}
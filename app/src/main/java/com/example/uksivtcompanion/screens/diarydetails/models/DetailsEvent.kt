package com.example.uksivtcompanion.screens.diarydetails.models

sealed class DetailsEvent {
    data class EnterScreen(val uid: String) : DetailsEvent()
    object PreviousDayClicked : DetailsEvent()
    object NextDayClicked : DetailsEvent()
    object OnSaveClick: DetailsEvent()
    object OnDeleteClick: DetailsEvent()
}
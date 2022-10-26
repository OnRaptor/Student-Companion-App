package com.example.uksivtcompanion.screens.diary.models

sealed class DiaryEvent {
    object EnterScreen : DiaryEvent()
    object PreviousDayClicked : DiaryEvent()
    object NextDayClicked : DiaryEvent()
    class OnDiaryClicked(val uid: String) : DiaryEvent()
    class OnDeleteClicked(val uid: String) : DiaryEvent()
}
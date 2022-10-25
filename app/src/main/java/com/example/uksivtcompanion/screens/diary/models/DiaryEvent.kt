package com.example.uksivtcompanion.screens.diary.models

sealed class DiaryEvent {
    object EnterScreen : DiaryEvent()
    object ReloadScreen : DiaryEvent()
    object PreviousDayClicked : DiaryEvent()
    object NextDayClicked : DiaryEvent()
    data class OnDiaryClick(val diaryUID:String): DiaryEvent()
}
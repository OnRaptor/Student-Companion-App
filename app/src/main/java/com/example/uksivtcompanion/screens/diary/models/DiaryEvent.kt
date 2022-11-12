package com.example.uksivtcompanion.screens.diary.models

import com.example.uksivtcompanion.data.entities.Diary

sealed class DiaryEvent {
    object EnterScreen : DiaryEvent()
    object PreviousDayClicked : DiaryEvent()
    object NextDayClicked : DiaryEvent()
    class OnDiaryClicked(val uid: String) : DiaryEvent()
    class OnDeleteClicked(val uid: String) : DiaryEvent()
    class OnSaveClicked(val item: Diary) : DiaryEvent()
    object OnEraseAllClicked : DiaryEvent()
}
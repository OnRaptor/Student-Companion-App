package com.example.uksivtcompanion.screens.schedule.models

import androidx.compose.runtime.MutableState
import com.example.uksivtcompanion.data.entities.Lesson
import com.example.uksivtcompanion.data.entities.Schedule
import com.example.uksivtcompanion.screens.schedule.Days

sealed class ScheduleViewState {
    object Loading : ScheduleViewState()
    data class Display(
        val lessons : List<Lesson>,
        val Day: String
    ) : ScheduleViewState()
    object NoData : ScheduleViewState()
    data class Editing(
        val lessons : List<Lesson>,
        val Day: String
    ) : ScheduleViewState()
}
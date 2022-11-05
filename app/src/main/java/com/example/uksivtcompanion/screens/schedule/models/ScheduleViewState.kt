package com.example.uksivtcompanion.screens.schedule.models

import com.example.uksivtcompanion.data.entities.Schedule

sealed class ScheduleViewState {
    object Loading : ScheduleViewState()
    data class Display(
        val schedule : Schedule
    ) : ScheduleViewState()
    object NoData : ScheduleViewState()
}
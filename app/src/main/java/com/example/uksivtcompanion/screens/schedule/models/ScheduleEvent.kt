package com.example.uksivtcompanion.screens.schedule.models

import com.example.uksivtcompanion.data.entities.Lesson

sealed class ScheduleEvent {
    object EnterScreen : ScheduleEvent()
    object PreviousDayClicked : ScheduleEvent()
    object NextDayClicked : ScheduleEvent()
    object CreateClicked : ScheduleEvent()
    object DeleteAll : ScheduleEvent()
    object Import : ScheduleEvent()
    object EditTimeSheet : ScheduleEvent()
    data class UpdateTimeSheet(
        val lessons: List<Lesson>
    ) : ScheduleEvent()
}
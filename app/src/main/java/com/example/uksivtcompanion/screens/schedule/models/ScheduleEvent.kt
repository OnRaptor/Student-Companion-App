package com.example.uksivtcompanion.screens.schedule.models

sealed class ScheduleEvent {
    object EnterScreen : ScheduleEvent()
    object PreviousDayClicked : ScheduleEvent()
    object NextDayClicked : ScheduleEvent()
    object EditTimeSheet : ScheduleEvent()
}
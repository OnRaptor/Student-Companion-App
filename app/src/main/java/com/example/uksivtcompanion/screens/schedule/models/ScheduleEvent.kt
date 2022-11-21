package com.example.uksivtcompanion.screens.schedule.models

import android.net.Uri
import com.example.uksivtcompanion.data.entities.Lesson

sealed class ScheduleEvent {
    object EnterScreen : ScheduleEvent()
    object PreviousDayClicked : ScheduleEvent()
    object NextDayClicked : ScheduleEvent()
    object CreateClicked : ScheduleEvent()
    object DeleteAll : ScheduleEvent()
    data class Import(val inputFile:String) : ScheduleEvent()
    data class Export(val outDir:String) : ScheduleEvent()
    object EditTimeSheet : ScheduleEvent()
    data class UpdateTimeSheet(
        val lessons: List<Lesson>
    ) : ScheduleEvent()
}
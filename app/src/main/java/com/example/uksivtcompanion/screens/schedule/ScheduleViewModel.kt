package com.example.uksivtcompanion.screens.schedule

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.dao.ScheduleDAO
import com.example.uksivtcompanion.data.entities.Lesson
import com.example.uksivtcompanion.data.entities.Schedule
import com.example.uksivtcompanion.screens.schedule.models.ScheduleEvent
import com.example.uksivtcompanion.screens.schedule.models.ScheduleViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class Days(val index:Int){
    Monday(0),
    Tuesday(1),
    Wednesday(2),
    Thursday(3),
    Friday(4),
    Saturday(5),
    Sunday(6);
}

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) : ViewModel(), EventHandler<ScheduleEvent>{
    private var daysIterator: Int = 0
    private val _scheduleViewState: MutableLiveData<ScheduleViewState> = MutableLiveData(ScheduleViewState.Loading)
    val scheduleViewState: LiveData<ScheduleViewState> = _scheduleViewState

    var schedule: Schedule = Schedule()
    var currentDay:Days = Days.Monday


    override fun obtainEvent(event: ScheduleEvent) {
        when (val currentState = _scheduleViewState.value) {
            is ScheduleViewState.Loading -> reduce(event, currentState)
            is ScheduleViewState.Display -> reduce(event, currentState)
            is ScheduleViewState.NoData  -> reduce(event, currentState)
            is ScheduleViewState.Editing  -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.Loading){
        when(event) {
            ScheduleEvent.EnterScreen -> fetchTimeSheet()
            else -> {}
        }
    }
    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.Display){
        when (event) {
            ScheduleEvent.NextDayClicked -> performNextClick()
            ScheduleEvent.PreviousDayClicked -> performPreviousClick()
            ScheduleEvent.EditTimeSheet -> performEditTimeSheetClick()
            ScheduleEvent.CreateClicked -> performEditTimeSheetClick()
            ScheduleEvent.DeleteAll -> performDeleteAllClick()
            is ScheduleEvent.Import -> performImportClick(event.inputFile)
            is ScheduleEvent.Export -> performExportClick(event.outDir)
            else -> {}
        }
    }
    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.NoData){
        when (event) {
            ScheduleEvent.EditTimeSheet -> performEditTimeSheetClick()
            is ScheduleEvent.Import -> performImportClick(event.inputFile)
            else -> {}
        }
    }
    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.Editing){
        when (event) {
            ScheduleEvent.EnterScreen -> fetchTimeSheet()
            is ScheduleEvent.UpdateTimeSheet -> updateTimeSheet(event.lessons)
            else -> {}
        }
    }

    private fun performImportClick(inputFile:String){
        viewModelScope.launch {
            scheduleDAO.importFile(inputFile)
            fetchTimeSheet()
        }
    }

    private fun performExportClick(outDir:String){
        viewModelScope.launch {
            scheduleDAO.setSchedule(schedule)
            scheduleDAO.exportFile(outDir)
        }
    }

    private fun performDeleteAllClick(){
        viewModelScope.launch {
            scheduleDAO.setSchedule(Schedule())
            fetchTimeSheet()
        }
    }

    private fun performEditTimeSheetClick(){
        viewModelScope.launch {
            _scheduleViewState.postValue(ScheduleViewState.Editing(getLessonsForCurrentDay(), currentDay.toString()))
        }
    }

    private fun performNextClick() {
        if (daysIterator == 6)
            daysIterator = -1

        daysIterator++
        currentDay = Days.values()[daysIterator]
        setLessonsForDay()
    }

    private fun performPreviousClick() {
        if (daysIterator == 0)
            daysIterator = 7
        daysIterator--
        currentDay = Days.values()[daysIterator]
        setLessonsForDay()
    }

    private fun updateTimeSheet(lessons: List<Lesson>){
        viewModelScope.launch {
            when (currentDay) {
                Days.Monday -> schedule.Monday.lessons = lessons
                Days.Tuesday -> schedule.Tuesday.lessons = lessons
                Days.Wednesday -> schedule.Wednesday.lessons = lessons
                Days.Thursday -> schedule.Thursday.lessons = lessons
                Days.Friday -> schedule.Friday.lessons = lessons
                Days.Saturday -> schedule.Saturday.lessons = lessons
                Days.Sunday -> schedule.Sunday.lessons = lessons
            }
            scheduleDAO.setSchedule(schedule)
            setLessonsForDay()
        }
    }

    private fun fetchTimeSheet(){
        viewModelScope.launch {
            schedule = scheduleDAO.getSchedule()
            if (schedule.Monday.lessons.isEmpty()){
                _scheduleViewState.postValue(ScheduleViewState.NoData)
                return@launch
            }
            setLessonsForDay()
        }
    }

    private fun setLessonsForDay()
    {
        viewModelScope.launch {
            _scheduleViewState.postValue(ScheduleViewState.Display(getLessonsForCurrentDay(), currentDay.toString()))
        }
    }

    private fun getLessonsForCurrentDay() : List<Lesson> =
         when(currentDay){
            Days.Monday -> schedule.Monday.lessons
            Days.Tuesday -> schedule.Tuesday.lessons
            Days.Wednesday -> schedule.Wednesday.lessons
            Days.Thursday -> schedule.Thursday.lessons
            Days.Friday ->  schedule.Friday.lessons
            Days.Saturday -> schedule.Saturday.lessons
            Days.Sunday -> schedule.Sunday.lessons
        }
}
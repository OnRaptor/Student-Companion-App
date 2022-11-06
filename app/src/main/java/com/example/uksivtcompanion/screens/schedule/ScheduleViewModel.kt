package com.example.uksivtcompanion.screens.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.dao.ScheduleDAO
import com.example.uksivtcompanion.screens.schedule.models.ScheduleEvent
import com.example.uksivtcompanion.screens.schedule.models.ScheduleViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) : ViewModel(), EventHandler<ScheduleEvent>{
    private val _scheduleViewState: MutableLiveData<ScheduleViewState> = MutableLiveData(ScheduleViewState.Loading)
    val scheduleViewState: LiveData<ScheduleViewState> = _scheduleViewState

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
            ScheduleEvent.EnterScreen -> getLessonsForDay()

            else -> {}
        }
    }

    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.Display){
        when (event) {
            ScheduleEvent.NextDayClicked -> performNextClick()
            ScheduleEvent.PreviousDayClicked -> performPreviousClick()
            else -> {}
        }
    }
    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.NoData){
        when (event) {
            ScheduleEvent.EditTimeSheet -> performEditTimeSheetClick()
            else -> {}
        }
    }
    private fun reduce(event : ScheduleEvent, currentState: ScheduleViewState.Editing){
        when (event) {
            ScheduleEvent.EnterScreen -> getLessonsForDay()
            else -> {}
        }
    }

    private fun performEditTimeSheetClick(){
        viewModelScope.launch {
            _scheduleViewState.postValue(ScheduleViewState.Editing)
        }
    }

    private fun performNextClick() {
        getLessonsForDay()
    }

    private fun performPreviousClick() {
        getLessonsForDay()
    }

    private fun updateData(){

    }

    private fun getLessonsForDay()
    {
        viewModelScope.launch {
            _scheduleViewState.postValue(ScheduleViewState.NoData)
        }
    }
}
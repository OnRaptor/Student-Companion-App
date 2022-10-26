package com.example.uksivtcompanion.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.repositories.DiaryRepository
import com.example.uksivtcompanion.screens.diary.models.DiaryEvent
import com.example.uksivtcompanion.screens.diary.models.DiaryViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel(), EventHandler<DiaryEvent>{

    private var currentDate: Date = Calendar.getInstance().time
    private val _diaryViewState: MutableLiveData<DiaryViewState> = MutableLiveData(DiaryViewState.Loading)
    val diaryViewState: LiveData<DiaryViewState> = _diaryViewState

    override fun obtainEvent(event: DiaryEvent) {
        when (val currentState = _diaryViewState.value) {
            is DiaryViewState.Loading -> reduce(event, currentState)
            is DiaryViewState.Display -> reduce(event, currentState)
            is DiaryViewState.Error -> reduce(event, currentState)
            is DiaryViewState.NoItems -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.Loading){
        when(event) {
            DiaryEvent.EnterScreen -> fetchDiariesForDate()
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.Display){
        when (event) {
            DiaryEvent.EnterScreen -> fetchDiariesForDate()
            DiaryEvent.NextDayClicked -> performNextClick(currentState.hasNextDay)
            DiaryEvent.PreviousDayClicked -> performPreviousClick()
            is DiaryEvent.OnDiaryClick -> performDiaryClick(
                hasNextDay = currentState.hasNextDay,
                diaryUID = event.diaryUID
            )
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.Error){
        when (event) {
            DiaryEvent.ReloadScreen -> fetchDiariesForDate(needsToRefresh = true)
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.NoItems){
        when (event) {
            DiaryEvent.ReloadScreen -> fetchDiariesForDate(needsToRefresh = true)
            DiaryEvent.EnterScreen -> fetchDiariesForDate()
            else -> {}
        }
    }

    private fun performDiaryClick(hasNextDay: Boolean, diaryUID: String) {

    }

    private fun performNextClick(hasNextDay: Boolean) {

    }

    private fun performPreviousClick() {

    }

    private fun getTitleForADay(): String {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate

        val difference = Calendar.getInstance().timeInMillis - calendar.timeInMillis
        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

        return when (TimeUnit.MILLISECONDS.toDays(difference)) {
            0L -> "Today"
            1L -> "Yesterday"
            else -> dateFormat.format(currentDate)
        }
    }

    private fun fetchDiariesForDate(
        date:String = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()),
        needsToRefresh: Boolean = false,
        setHasNextDay: Boolean = false){
        if (needsToRefresh) {
            _diaryViewState.postValue(DiaryViewState.Loading)
        }

        viewModelScope.launch {
            try{
                val diaries = diaryRepository.fetchDiariesForDate(date)
                if (diaries.isNotEmpty())
                    _diaryViewState.postValue(DiaryViewState.Display(
                        items = diaries,
                        hasNextDay = setHasNextDay,
                        title = getTitleForADay()
                    ))
                else
                    _diaryViewState.postValue(DiaryViewState.NoItems)
            }
            catch (e:java.lang.Exception){
                _diaryViewState.postValue(DiaryViewState.Error)
            }

        }
    }
}

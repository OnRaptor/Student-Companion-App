package com.example.uksivtcompanion.screens.diary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.data.repositories.DiaryRepository
import com.example.uksivtcompanion.screens.diary.models.DiaryEvent
import com.example.uksivtcompanion.screens.diary.models.DiaryViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel(), EventHandler<DiaryEvent>{

    private var dateIterator:Int = 0
    private lateinit var possibleDates:MutableList<Date>
    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
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
            DiaryEvent.EnterScreen -> updateData()
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.Display){
        when (event) {
            DiaryEvent.NextDayClicked -> performNextClick()
            DiaryEvent.PreviousDayClicked -> performPreviousClick()
            is DiaryEvent.OnDiaryClicked -> performDiaryClick(event.uid)
            is DiaryEvent.OnDeleteClicked -> performDeleteClick(event.uid)
            else -> {}
        }
    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.Error){

    }

    private fun reduce(event : DiaryEvent, currentState: DiaryViewState.NoItems){
        when (event) {
            DiaryEvent.EnterScreen -> updateData()
            DiaryEvent.NextDayClicked -> performNextClick()
            DiaryEvent.PreviousDayClicked -> performPreviousClick()
            is DiaryEvent.OnDiaryClicked -> performDiaryClick(event.uid)
            else -> {}
        }
    }

    private fun performDiaryClick(uid:String) {
        viewModelScope.launch {
            diaryRepository.addOrUpdateDiary(DiaryItem(
                uid = uid.ifEmpty { UUID.randomUUID().toString() },
                title = mutableStateOf(""),
                text = mutableStateOf(""),
                date = mutableStateOf(formatter.format(possibleDates[dateIterator]))
            ))
            _diaryViewState.postValue(DiaryViewState.Loading)
        }
    }

    private fun performNextClick() {
        try {
            possibleDates[dateIterator + 1]
        } catch (e: java.lang.IndexOutOfBoundsException){
            return
        }
        dateIterator++
        fetchDiariesForDate()
    }

    private fun performPreviousClick() {
        try {
            possibleDates[dateIterator - 1]
        } catch (e: java.lang.IndexOutOfBoundsException){
            return
        }
        dateIterator--
        fetchDiariesForDate()
    }

    private fun performDeleteClick(uid:String){
        viewModelScope.launch {
            val record = diaryRepository.findByUID(uid)
            diaryRepository.deleteDiary(uid)
            fetchDiariesForDate(record.date)
        }
    }

    private fun updateData(){
        viewModelScope.launch {
            possibleDates = diaryRepository.getDiaryDates().toMutableList()
            if (possibleDates.size == 0)
                possibleDates.add(Date())
            fetchDiariesForDate()
        }
    }

    private fun getTitleForCurrentDay(): String {
        val today = LocalDateTime.ofInstant(Calendar.getInstance().time.toInstant(), ZoneId.systemDefault())
        val target = LocalDateTime.ofInstant(possibleDates[dateIterator].toInstant(), ZoneId.systemDefault())
        val difference = ChronoUnit.DAYS.between(today, target)

        if (today.dayOfMonth - target.dayOfMonth == -1)
            return "Завтра"

        return when (difference) {
            0L -> "Сегодня"
            -1L -> "Вчера"
            else -> formatter.format(possibleDates[dateIterator])
        }
    }

    private fun fetchDiariesForDate(
        date:String = formatter.format(possibleDates[dateIterator])
    )
    {
        viewModelScope.launch {
            try{
                val diaries = diaryRepository.fetchDiariesForDate(date)
                if (diaries.isNotEmpty())
                    _diaryViewState.postValue(DiaryViewState.Display(
                        items = diaries,
                        date = mutableStateOf(getTitleForCurrentDay())
                    ))
                else
                    _diaryViewState.postValue(DiaryViewState.NoItems(
                        mutableStateOf(getTitleForCurrentDay()))
                    )
            }
            catch (e:java.lang.Exception){
                _diaryViewState.postValue(DiaryViewState.Error)
            }
        }
    }
}

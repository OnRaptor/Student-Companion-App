package com.example.uksivtcompanion.screens.diary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.data.repositories.DiaryRepository
import com.example.uksivtcompanion.screens.diary.models.DetailsEvent
import com.example.uksivtcompanion.screens.diary.models.DetailsViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel(), EventHandler<DetailsEvent>
{
    private lateinit var item:DiaryItem
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    private val _detailsViewState: MutableLiveData<DetailsViewState> = MutableLiveData(
        DetailsViewState.Loading)
    val detailsViewState: LiveData<DetailsViewState> = _detailsViewState

    override fun obtainEvent(event: DetailsEvent) {
        when (val currentState = _detailsViewState.value) {
            is DetailsViewState.Loading -> reduce(event, currentState)
            is DetailsViewState.Display -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event : DetailsEvent, currentState: DetailsViewState.Display){
        when(event) {
            is DetailsEvent.OnSaveClick -> performSaveClick()
            is DetailsEvent.OnDeleteClick -> performDeleteClick()
            is DetailsEvent.PreviousDayClicked -> performPreviousClick()
            is DetailsEvent.NextDayClicked -> performNextClick()
            is DetailsEvent.EnterScreen -> fetchDiariesByUID(event.uid)
            is DetailsEvent.OnDestroy -> {
                if (item.title.value.isEmpty() && item.text.value.isEmpty())
                    performDeleteClick()
            }
            else -> {}
        }
    }

    private fun reduce(event : DetailsEvent, currentState: DetailsViewState.Loading){
        when(event) {
            is DetailsEvent.EnterScreen -> fetchDiariesByUID(event.uid)
            else -> {}
        }
    }

    private fun performNextClick() {
        item.date.value = LocalDate.parse(item.date.value, formatter).plusDays(1).format(formatter)
        _detailsViewState.postValue(DetailsViewState.Display(item))
    }

    private fun performPreviousClick() {
        item.date.value = LocalDate.parse(item.date.value, formatter).minusDays(1).format(formatter)
        _detailsViewState.postValue(DetailsViewState.Display(item))
    }

    private fun performSaveClick(){
        viewModelScope.launch {
            diaryRepository.addOrUpdateDiary(item)
        }
    }

    private fun performDeleteClick(){
        viewModelScope.launch {
            diaryRepository.deleteDiary(item.uid)
        }
    }

    private fun fetchDiariesByUID(uid:String?){
        if (uid == null)
            return
        viewModelScope.launch {
            try {
                val record = diaryRepository.findByUID(uid)
                item = DiaryItem(
                    record.uid,
                    mutableStateOf(record.title),
                    mutableStateOf(record.desc),
                    mutableStateOf(record.date)
                )
                _detailsViewState.postValue(
                    DetailsViewState.Display(
                    item
                ))
            }
            catch (e:Exception){

            }
        }
    }

}
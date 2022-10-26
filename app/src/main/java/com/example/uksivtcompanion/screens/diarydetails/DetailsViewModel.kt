package com.example.uksivtcompanion.screens.diarydetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.data.repositories.DiaryRepository
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsEvent
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsViewState
import com.example.uksivtcompanion.services.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel(), EventHandler<DetailsEvent>
{
    private val _detailsViewState: MutableLiveData<DetailsViewState> = MutableLiveData(DetailsViewState.Loading)
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
            is DetailsEvent.OnSaveClick -> performSaveClick(currentState.diaryItem)
            is DetailsEvent.OnDeleteClick -> performDeleteClick(currentState.diaryItem)
            else -> {}
        }
    }

    private fun reduce(event : DetailsEvent, currentState: DetailsViewState.Loading){
        when(event) {
            is DetailsEvent.EnterScreen -> fetchDiariesByUID(event.uid)
            else -> {}
        }
    }

    private fun performNextDayClicked(){

    }

    private fun performSaveClick(diaryItem:DiaryItem){
        viewModelScope.launch {
            if (diaryItem.uid == "")
                diaryItem.uid = UUID.randomUUID().toString()
            diaryRepository.addOrUpdateDiary(diaryItem)
        }
    }

    private fun performDeleteClick(diaryItem:DiaryItem){
        viewModelScope.launch {
            if (diaryItem.uid == "")
                return@launch
            diaryRepository.deleteDiary(diaryItem.uid)
        }
    }

    private fun fetchDiariesByUID(uid:String){
        if (uid.isEmpty()) {
            _detailsViewState.postValue(
                DetailsViewState.Display(
                    DiaryItem(
                        "",
                        mutableStateOf(""),
                        mutableStateOf(""),
                        mutableStateOf(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))
                    )
                )
            )
            return
        }
        viewModelScope.launch {
            try {
                val record = diaryRepository.findByUID(uid)
                _detailsViewState.postValue(DetailsViewState.Display(
                    DiaryItem(
                        record.uid,
                        mutableStateOf(record.title),
                        mutableStateOf(record.desc),
                        mutableStateOf(record.date)
                    )
                ))
            }
            catch (e:Exception){

            }
        }
    }

}
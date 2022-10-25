package com.example.uksivtcompanion.screens.diarydetails

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
            DetailsEvent.EnterScreen -> fetchDiariesByUID("")
            is DetailsEvent.OnSaveClick -> performSaveClick(event.diaryItem)
            else -> {}
        }
    }

    private fun reduce(event : DetailsEvent, currentState: DetailsViewState.Loading){
        when(event) {
            DetailsEvent.EnterScreen -> fetchDiariesByUID("")
            else -> {}
        }
    }

    private fun performNextDayClicked(){

    }

    private fun performSaveClick(diaryItem: DiaryItem){
        viewModelScope.launch {
            diaryRepository.addNewDiary(diaryItem)
        }
    }

    private fun fetchDiariesByUID(uid:String){
        if (uid.isEmpty())
            return


    }

}
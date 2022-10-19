package com.example.uksivtcompanion.screens.diary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(

) : ViewModel(){
    fun getPreviewOfDiaries() : List<DiaryItemPreview>{
        //retrieve data from file
        return listOf(
            DiaryItemPreview("АБОБА","Описание"),
            DiaryItemPreview("АБОБА","Описание"),
            DiaryItemPreview("АБОБА","Описание"),
            DiaryItemPreview("АБОБА","Описание")
        )
    }


}

data class DiaryItemPreview(val title:String, val description:String)
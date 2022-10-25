package com.example.uksivtcompanion.data.repositories

import androidx.compose.runtime.mutableStateOf
import com.example.uksivtcompanion.data.dao.DiaryDAO
import com.example.uksivtcompanion.data.entities.Diary
import com.example.uksivtcompanion.data.entities.DiaryItem
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDAO: DiaryDAO
){
    suspend fun fetchDiariesForDate(date:String) : List<DiaryItem> = diaryDAO.getAll().mapNotNull {
        if (it.date == date)
            DiaryItem(
                it.uid,
                mutableStateOf(it.title),
                mutableStateOf(it.desc),
                mutableStateOf(it.date),
            )
        else null
    }

    suspend fun addNewDiary(diary:DiaryItem){
        diaryDAO.insertAll(Diary(
            diary.uid,
            diary.title.value,
            diary.text.value,
            diary.date.value
        ))
    }

    suspend fun updateDiary(diary:DiaryItem){
        diaryDAO.update(Diary(
            diary.uid,
            diary.title.value,
            diary.text.value,
            diary.date.value
        ))
    }

    suspend fun deleteDiary(diary:DiaryItem){
        diaryDAO.delete(Diary(
            diary.uid,
            diary.title.value,
            diary.text.value,
            diary.date.value
        ))
    }
}
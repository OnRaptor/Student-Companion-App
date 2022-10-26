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

    suspend fun addOrUpdateDiary(diary:DiaryItem){
        val records = fetchDiariesForDate(diary.date.value)
        if (records.find { it.uid == diary.uid } == null)
            diaryDAO.insertAll(Diary(
                diary.uid,
                diary.title.value,
                diary.text.value,
                diary.date.value
            ))
        else
            diaryDAO.update(Diary(
                diary.uid,
                diary.title.value,
                diary.text.value,
                diary.date.value
            ))
    }


    suspend fun deleteDiary(uid: String){
        val record = findByUID(uid)
        diaryDAO.delete(Diary(
            record.uid,
            record.title,
            record.desc,
            record.date
        ))
    }

    suspend fun findByUID(uid:String) : Diary{
        return diaryDAO.findByUID(uid)
    }
}
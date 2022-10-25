package com.example.uksivtcompanion.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uksivtcompanion.data.dao.DiaryDAO
import com.example.uksivtcompanion.data.entities.Diary

@Database(entities = [Diary::class], version=1)
abstract class DiaryDataBase : RoomDatabase() {
    abstract fun diaryDAO(): DiaryDAO
}
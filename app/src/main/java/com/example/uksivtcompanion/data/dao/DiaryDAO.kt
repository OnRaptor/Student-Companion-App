package com.example.uksivtcompanion.data.dao

import androidx.room.*
import com.example.uksivtcompanion.data.entities.Diary

@Dao
interface DiaryDAO {
    @Query("SELECT * FROM diary")
    suspend fun getAll():List<Diary>

    @Query("SELECT * FROM diary WHERE date LIKE :dateToFind")
    suspend fun getAllByDate(dateToFind:String): List<Diary>

    @Query("SELECT * FROM diary WHERE uid LIKE :uid")
    suspend fun findByUID(uid:String): Diary?

    @Query("SELECT DISTINCT date FROM diary")
    suspend fun getAllDates() : List<String>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: Diary)

    @Insert
    suspend fun insertAll(vararg diaries:Diary)

    @Delete
    suspend fun delete(diary: Diary)
}
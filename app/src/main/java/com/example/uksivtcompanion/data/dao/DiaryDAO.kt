package com.example.uksivtcompanion.data.dao

import androidx.room.*
import com.example.uksivtcompanion.data.entities.Diary

@Dao
interface DiaryDAO {
    @Query("SELECT * FROM diary")
    fun getAll():List<Diary>

    @Query("SELECT * FROM diary WHERE date LIKE :dateToFind")
    fun getAllByDate(dateToFind:String): List<Diary>

    @Query("SELECT * FROM diary WHERE uid LIKE :uid")
    fun findByUID(uid:String): Diary?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: Diary)

    @Insert
    fun insertAll(vararg diaries:Diary)

    @Delete
    fun delete(diary: Diary)
}
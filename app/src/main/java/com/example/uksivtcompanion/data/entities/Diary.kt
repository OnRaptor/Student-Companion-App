package com.example.uksivtcompanion.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diary (
        @PrimaryKey val uid: String,
        @ColumnInfo(name = "title") val title:String,
        @ColumnInfo(name = "desc") val desc:String,
        @ColumnInfo(name = "date") val date:String,
)
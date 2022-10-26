package com.example.uksivtcompanion.data.entities

import androidx.compose.runtime.MutableState

data class DiaryItem(
    var uid:String, var title: MutableState<String>, var text: MutableState<String>,
    var date: MutableState<String>
)
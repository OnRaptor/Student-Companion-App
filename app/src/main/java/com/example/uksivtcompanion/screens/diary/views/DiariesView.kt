package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.uksivtcompanion.data.entities.DiaryItem

@Composable
fun DiariesView(
    navController: NavController,
    diaries: List<DiaryItem>
){
    LazyColumn(){
        diaries.forEach { diaryItem ->
            item  {
            DiaryView(navController = navController, item = diaryItem)
            }
        }
    }

}
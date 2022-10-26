package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.DialogHost
import androidx.navigation.compose.DialogNavigator
import com.example.uksivtcompanion.data.entities.DiaryItem

@Composable
fun DiariesView(
    navController: NavController,
    diaries: List<DiaryItem>,
    onLongTapCallback:(uid:String)->Unit
){
        LazyColumn() {
            diaries.forEach { diaryItem ->
                item {
                    DiaryView(
                        navController = navController,
                        item = diaryItem,
                        onLongTapCallBack = { onLongTapCallback(diaryItem.uid) }
                    )
                }
            }
        }

}
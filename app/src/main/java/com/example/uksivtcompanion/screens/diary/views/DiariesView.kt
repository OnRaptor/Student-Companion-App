package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.uksivtcompanion.data.entities.DiaryItem

@Composable
fun DiariesView(
    onDiaryClick: (uid:String) -> Unit,
    diaries: List<DiaryItem>,
    onLongTapCallback:(uid:String)->Unit
){
        LazyColumn {
            diaries.forEach { diaryItem ->
                item {
                    DiaryView(
                        onDiaryClick,
                        item = diaryItem,
                        onLongTapCallBack = { onLongTapCallback(diaryItem.uid) }
                    )
                }
            }
        }

}
package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uksivtcompanion.screens.components.DateSwitch

@Composable
fun DiaryViewLayout(
    title:MutableState<String>,
    onNextClick:() -> Unit,
    onPrevClick:() -> Unit,
    content: @Composable () -> Unit
){
    Column(Modifier.fillMaxSize()) {
        DateSwitch(
            title = title,
            onNextDayClick = onNextClick,
            onPrevDayClick = onPrevClick
        )
        Spacer(Modifier.padding(4.dp))
        content()
    }
}
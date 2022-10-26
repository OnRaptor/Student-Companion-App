package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DiaryViewError(){
    Box(Modifier.fillMaxSize()){
        Text(
            text = "Ачибка😞",
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
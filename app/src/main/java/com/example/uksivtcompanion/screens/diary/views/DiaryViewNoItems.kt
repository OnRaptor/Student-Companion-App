package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoBackpack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import java.util.*

@Composable
fun DiaryViewNoItems(
    OnDiaryClick:(uid:String)->Unit
){
    Box(Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center)) {
            Row{
                Icon(Icons.Outlined.NoBackpack, contentDescription = "No items")
                Text(
                    text = "Нет дз",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
            ElevatedButton(onClick = {
                val uid = UUID.randomUUID().toString()
                OnDiaryClick(uid)
            }) {
                Text("Нажмите чтобы добавить")
            }
        }
    }
}
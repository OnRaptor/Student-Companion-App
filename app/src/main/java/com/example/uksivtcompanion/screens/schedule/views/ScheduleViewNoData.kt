package com.example.uksivtcompanion.screens.schedule.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleViewNoData(
    onCreateCallback: () -> Unit
){
    Box(Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .align(Alignment.Center)
            .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Нет расписания")
            Button(onClick = onCreateCallback) {
                Text("Создать")
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text("Импорт")
            }
        }
    }
}
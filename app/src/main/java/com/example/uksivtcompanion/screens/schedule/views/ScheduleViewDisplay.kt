package com.example.uksivtcompanion.screens.schedule.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uksivtcompanion.screens.components.DateSwitch
import com.example.uksivtcompanion.screens.schedule.ScheduleViewModel

@Composable
fun ScheduleViewDisplay(
    viewModel: ScheduleViewModel
){
    Column(Modifier.verticalScroll(rememberScrollState())) {
        DateSwitch()
        TimeSheet()
        Spacer(modifier = Modifier.height(25.dp))
    }
}


@Composable
fun TimeSheet(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                Color(434343)
            )){
            Text("Название", Modifier.fillMaxWidth(0.5f), fontWeight = FontWeight.SemiBold)
            Text("Каб", Modifier.fillMaxWidth(0.35f), fontWeight = FontWeight.SemiBold)
            Text("Время", Modifier.fillMaxWidth(0.7f), fontWeight = FontWeight.SemiBold)
        }
        TimeSheetItem()
        Divider()
        TimeSheetItem()
        Divider()
        TimeSheetItem()
        Divider()
        TimeSheetItem()
        Divider()
        TimeSheetItem()
        Divider()
        TimeSheetItem()
    }
}

@Composable
fun TimeSheetItem(){
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .background(
            Color(434343)
        )){
        Text("Математика", Modifier.fillMaxWidth(0.5f))
        Text("213", Modifier.fillMaxWidth(0.35f))
        Text("9:30-11:00", Modifier.fillMaxWidth(0.7f))
    }
}

package com.example.uksivtcompanion.screens.schedule.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.uksivtcompanion.data.entities.Lesson
import com.example.uksivtcompanion.screens.components.DateSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleViewDisplay(
    lessons:List<Lesson>,
    day: MutableState<String>,
    nextDayClick: () -> Unit,
    prevDayClick: () -> Unit,
    onEditClick: () -> Unit,
    onCreateClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onImportClick: () -> Unit
){
    Box(modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                title = { Text("Расписание") },
                navigationIcon = { IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Timelapse, "")
                } },
                actions = {
                    IconButton(onClick = onDeleteAllClick) {
                        Icon(Icons.Filled.DeleteSweep, "")
                    }
                    IconButton(onClick = onImportClick) {
                        Icon(Icons.Filled.Upload, "",
                            modifier = Modifier.rotate(180f))//xddd
                    }
                }
            )
            DateSwitch(day, prevDayClick, nextDayClick)
            if (lessons.isNotEmpty())
                TimeSheet(lessons, onEditClick)
            else
                Text(
                    "Добавьте данные о предметах",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            Spacer(modifier = Modifier.height(25.dp))
        }
        FloatingActionButton(
            onClick = onCreateClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            contentColor = Color.White
        ) {
            if (lessons.isEmpty())
                Icon(Icons.Filled.Add, "")
            else
                Icon(Icons.Filled.Edit, "")
        }
    }

}


@Composable
fun TimeSheet(lessons:List<Lesson>, onEditClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable { onEditClick() },
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                Color(434343)
            )){
            Text("Название", Modifier.fillMaxWidth(0.5f),
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            Text("Каб", Modifier.fillMaxWidth(0.35f),
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            Text("Время", Modifier.fillMaxWidth(0.7f),
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
        }
        LazyColumn{
            items(items=lessons){
                Divider(Modifier.padding(5.dp))
                TimeSheetItem(it)
            }
        }
    }
}

@Composable
fun TimeSheetItem(lesson: Lesson){
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .background(
            Color(434343)
        )){
        Text(lesson.Name,
            Modifier.fillMaxWidth(0.5f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(lesson.Cab,
            Modifier.fillMaxWidth(0.35f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(lesson.Time,
            Modifier.fillMaxWidth(0.7f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

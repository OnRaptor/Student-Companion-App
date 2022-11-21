package com.example.uksivtcompanion.screens.schedule.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
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
    onImportClick: (inputFile:String) -> Unit,
    onExportClick: (outDir:String) -> Unit
){
    val (showDialog, setDialog) = remember{ mutableStateOf(false) }

    val launcherForExportChooser = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            uri -> onExportClick(uri?.toString() ?: return@rememberLauncherForActivityResult)
    }

    val launcherForImportChooser = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { onImportClick(it.toString()) }
    }

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
                    IconButton(onClick = { setDialog(true) }) {
                        Icon(Icons.Filled.ImportExport, "")
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
    if (showDialog)
        Dialog(onDismissRequest = { setDialog(false) }) {
            Card(
                Modifier
                    .fillMaxWidth()){
                Text(
                    "Импорт/Экспорт",
                    fontSize = 19.sp,
                    modifier = Modifier.padding(5.dp),
                    color = Color.Black
                )
                Divider()
                Column(modifier = Modifier
                    .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(onClick = {
                        launcherForImportChooser.launch("application/json")
                    }) {
                        Text("Импорт")
                    }
                    Text("Вам нужно будет выбрать файл импорта",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Divider(Modifier.padding(5.dp))
                    Button(onClick = {
                        launcherForExportChooser.launch(null)
                    }) {
                        Text("Экспорт")
                    }
                    Text(
                        "Вам нужно будет выбрать директорию где будет сохранен экспортируемый файл",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    TextButton(onClick = { setDialog(false) }, modifier = Modifier.fillMaxWidth()) {
                        Text("Отмена")
                    }
                }
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

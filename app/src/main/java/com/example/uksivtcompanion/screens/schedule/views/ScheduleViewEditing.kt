package com.example.uksivtcompanion.screens.schedule.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.uksivtcompanion.data.entities.Lesson
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleViewEditing(
    onBackPressCallback: () -> Unit
){
    val lessons = remember {
        mutableStateListOf<Lesson>()
    }
    val lazyColumnState = rememberLazyListState()
    val isCreateDialogVisible = remember {
        mutableStateOf(false)
    }
    val isEditDialogVisible = remember {
        mutableStateOf(false)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text("Monday") },
            navigationIcon = { IconButton(onClick = onBackPressCallback) {
                Icon(Icons.Filled.ArrowBack, "")
            } }
        )
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()){
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Предметы",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(state = lazyColumnState){
                    itemsIndexed(items = lessons)
                    { index, it ->
                        if (index != 0)
                            Divider(Modifier.padding(4.dp))
                        LessonItem(it) { isEditDialogVisible.value = true }
                    }
                }
                TextButton(onClick = {
                    isCreateDialogVisible.value = true
                    lessons.add(Lesson(getRandomString(Random().nextInt(15)), 231, "11:00 - 12:30"))
                }) {
                    Row() {
                        Icon(Icons.Filled.Add, contentDescription = "")
                        Text("Добавить предмет")
                    }
                }
            }
        }
        Text(
            "Тап по предмету для изменения",
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
    }
    if (isCreateDialogVisible.value)
        Dialog(onDismissRequest = { isCreateDialogVisible.value = false }) {
            CreateLessonItemDialog()
        }
    if (isEditDialogVisible.value)
        Dialog(onDismissRequest = { isEditDialogVisible.value = false }) {
            EditLessonItemDialog()
        }
}


@Composable
fun LessonItem(lesson: Lesson, onItemClick: () -> Unit){
    Row(modifier = Modifier
        .height(30.dp)
        .fillMaxWidth()
        .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(lesson.Name,
            modifier = Modifier.width(170.dp),
            overflow = TextOverflow.Ellipsis
        )
        Text(lesson.Cab.toString(),
            modifier = Modifier.width(30.dp)
        )
        Text(lesson.Time,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.End
        )
    }
}

@Preview(widthDp = 300)
@Composable
fun LessonItemPreview(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { },horizontalArrangement = Arrangement.SpaceBetween) {
        Text("asdaasdasdasdassd",
            modifier = Modifier.width(150.dp),
            overflow = TextOverflow.Ellipsis
        )
        Text("231",
            modifier = Modifier.width(30.dp)
        )
        Text("11:00 - 12:30",
            modifier = Modifier.width(100.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 250)
@Composable
fun CreateLessonItemDialog(){
    val (lessonName, setLessonName) = remember {
        mutableStateOf("")
    }
    val (cab, setCab) = remember {
        mutableStateOf("")
    }
    val (time, setTime) = remember {
        mutableStateOf("")
    }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(
                "Заполните данные",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            TextField(
                value = lessonName,
                onValueChange = setLessonName,
                singleLine = true,
                placeholder = { Text(text = "Математика")},
                supportingText = {Text("Название предмета")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = cab,
                onValueChange = setCab,
                singleLine = true,
                placeholder = { Text(text = "213")},
                supportingText = {Text("Кабинет")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = time,
                onValueChange = setTime,
                singleLine = true,
                placeholder = { Text(text = "11:15-12:45")},
                supportingText = {Text("Время")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextButton(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), onClick = {}) {
                Text("OK")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun EditLessonItemDialog(){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)){
        val (lessonName, setLessonName) = remember {
            mutableStateOf("")
        }
        val (cab, setCab) = remember {
            mutableStateOf("")
        }
        val (time, setTime) = remember {
            mutableStateOf("")
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(
                "Изменить данные",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            TextField(
                value = lessonName,
                onValueChange = setLessonName,
                singleLine = true,
                supportingText = { Text(text = "Название предмета")},
                placeholder = {Text("Математика")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = cab,
                onValueChange = setCab,
                singleLine = true,
                supportingText = { Text(text = "Кабинет")},
                placeholder = {Text("231")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = time,
                onValueChange = setTime,
                singleLine = true,
                supportingText = { Text(text = "Время")},
                placeholder = {Text("11:15-12:45")},
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            TextButton(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                onClick = {},
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
            ) {
                Text("Удалить предмет")
            }
            TextButton(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), onClick = {}) {
                Text("Применить")
            }
        }
    }

}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
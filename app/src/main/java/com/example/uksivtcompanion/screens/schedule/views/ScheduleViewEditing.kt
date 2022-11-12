package com.example.uksivtcompanion.screens.schedule.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.uksivtcompanion.data.entities.Lesson

class LessonItemUiState (name:String = "", cab:String = "", time:String=""){
    var Name: MutableState<String> = mutableStateOf(name)
    var Cab: MutableState<String> = mutableStateOf(cab)
    var Time: MutableState<String> = mutableStateOf(time)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleViewEditing(
    lessons:List<Lesson>,
    day: String,
    onBackPressCallback: () -> Unit,
    onApplyTimeSheet: (lessons: List<Lesson>) -> Unit
){
    val navController = rememberNavController()
    val mutableLessons : SnapshotStateList<LessonItemUiState> = remember {
        lessons.map { LessonItemUiState(it.Name, it.Cab, it.Time) }.toMutableStateList()
    }
    val lazyColumnState = rememberLazyListState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text(day) },
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
                    itemsIndexed(items = mutableLessons)
                    { index, it ->
                        if (index != 0)
                            Divider(Modifier.padding(4.dp))
                        LessonItem(it) {
                            navController.navigate("edit?" +
                                    "Name=${it.Name.value}?" +
                                    "Cab=${it.Cab.value}?" +
                                    "Time=${it.Time.value}?" +
                                    "index=$index")
                        }
                    }
                }
                if (mutableLessons.count() < 6)
                    TextButton(onClick = {
                        navController.navigate("create")
                    }) {
                        Row {
                            Icon(Icons.Filled.Add, contentDescription = "")
                            Text("Добавить предмет")
                        }
                    }

                TextButton(onClick = { onApplyTimeSheet(mutableLessons.map {
                    Lesson(it.Name.value, it.Cab.value, it.Time.value)})
                }
                ) {
                    Row {
                        Icon(Icons.Filled.Save, contentDescription = "")
                        Text("Сохранить")
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
    NavHost(navController = navController, startDestination = "Default"){
        composable("Default"){}
        dialog("create"){
            CreateLessonItemDialog { item ->
                mutableLessons.add(item)
                navController.popBackStack()
            }
        }
        dialog("edit?Name={Name}?Cab={Cab}?Time={Time}?index={ind}"){
            EditLessonItemDialog(
                it.arguments?.getString("Name"),
                it.arguments?.getString("Cab"),
                it.arguments?.getString("Time"),
                it.arguments?.getString("ind"),
                onConfirm = { index, item ->
                    mutableLessons[checkNotNull(index?.toInt())] = item
                    navController.popBackStack()
                },
                onDelete = { index ->
                    mutableLessons.removeAt(checkNotNull(index?.toInt()))
                    navController.popBackStack()
                })
        }
    }
}


@Composable
fun LessonItem(lesson: LessonItemUiState, onItemClick: () -> Unit){
    Row(modifier = Modifier
        .height(30.dp)
        .fillMaxWidth()
        .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(lesson.Name.value,
            modifier = Modifier.width(170.dp),
            overflow = TextOverflow.Ellipsis
        )
        Text(lesson.Cab.value,
            modifier = Modifier.width(30.dp),
            overflow = TextOverflow.Ellipsis
        )
        Text(lesson.Time.value,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLessonItemDialog(
    onConfirm: (item:LessonItemUiState) -> Unit
){
    val item = remember {
        LessonItemUiState()
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
            CorrectTextField(item.Name.value, "Название") { value -> item.Name.value = value }
            CorrectTextField(item.Cab.value, "Кабинет", keyboardType = KeyboardType.Number) { value -> item.Cab.value = value }
            CorrectTextField(item.Time.value, "Время", keyboardType = KeyboardType.Number) { value -> item.Time.value = value }
            TextButton(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), onClick = { onConfirm(item) }) {
                Text("OK")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLessonItemDialog(
    Name:String?,
    Cab:String?,
    Time:String?,
    index: String?,
    onConfirm: (index:String?,item:LessonItemUiState) -> Unit,
    onDelete: (index:String?) -> Unit
){
    val item = LessonItemUiState(Name!!, Cab!!, Time!!)
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)){
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
            CorrectTextField(item.Name.value, "Название") { value -> item.Name.value = value }
            CorrectTextField(item.Cab.value, "Кабинет", keyboardType = KeyboardType.Number) { value -> item.Cab.value = value }
            CorrectTextField(item.Time.value, "Время", keyboardType = KeyboardType.Number) { value -> item.Time.value = value }
            TextButton(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                onClick = { onDelete(index) },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
            ) {
                Text("Удалить предмет")
            }
            TextButton(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), onClick = { onConfirm(index, item) } ) {
                Text("Применить")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrectTextField(v:String, supportText:String, keyboardType: KeyboardType = KeyboardType.Text, onValueChangeCallback:(value:String) -> Unit){
    TextField(
        value = v,
        onValueChange = onValueChangeCallback,
        singleLine = true,
        supportingText = { Text(text = supportText)},
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            KeyboardCapitalization.Words,
            true,
            keyboardType,
            imeAction = ImeAction.Next
        )
    )
}
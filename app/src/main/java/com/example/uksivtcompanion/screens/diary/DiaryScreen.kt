package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoCell
import androidx.compose.material.icons.filled.NotAccessible
import androidx.compose.material.icons.outlined.NoCell
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uksivtcompanion.screens.components.DateSwitch
import com.example.uksivtcompanion.screens.diary.models.DiaryEvent
import com.example.uksivtcompanion.screens.diary.models.DiaryViewState
import com.example.uksivtcompanion.screens.diary.views.DiariesView
import com.example.uksivtcompanion.screens.diary.views.DiaryViewLoading
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DiaryScreen(navController: NavController,
                viewModel: DiaryViewModel = hiltViewModel()){
    val date = rememberSaveable{ mutableStateOf(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))}
    val viewState = viewModel.diaryViewState.observeAsState()

    Box(Modifier.fillMaxSize()){
        Column{
            DateSwitch(
                date
            )
            when(val state = viewState.value){
                 DiaryViewState.Loading -> DiaryViewLoading()
                 DiaryViewState.Error -> {
                     Box(Modifier.fillMaxSize()){
                         Text(
                             text = "Ачибка😞",
                             color = Color.Red,
                             modifier = Modifier.align(Alignment.Center)
                         )
                     }
                 }
                 DiaryViewState.NoItems -> {
                     Box(Modifier.fillMaxSize()){
                         Column(modifier = Modifier.align(Alignment.Center)) {
                             Row{
                                 Icon(Icons.Outlined.NoCell, contentDescription = "No items")
                                 Text(
                                     text = "Нет дз",
                                     color = Color.Gray
                                 )
                             }
                             ElevatedButton(onClick = { navController.navigate("details")}) {
                                 Text("Нажмите чтобы добавить")
                             }
                         }

                     }
                 }
                 is DiaryViewState.Display -> DiariesView(
                     navController,
                     state.items
                 )
                else -> {}
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            FloatingActionButton(
                onClick = { navController.navigate("details") },
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(Icons.Rounded.Add, "", tint = Color.White)
            }
        }

        LaunchedEffect(key1 = viewState, block = {
            viewModel.obtainEvent(event = DiaryEvent.EnterScreen)
        })
    }
}




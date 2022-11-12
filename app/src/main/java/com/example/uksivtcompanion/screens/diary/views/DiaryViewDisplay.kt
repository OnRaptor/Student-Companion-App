package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.uksivtcompanion.screens.diary.DiaryViewModel
import com.example.uksivtcompanion.screens.diary.models.DiaryEvent
import com.example.uksivtcompanion.screens.diary.models.DiaryViewState
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryViewDisplay(
    state: DiaryViewState.Display,
    viewModel:DiaryViewModel
) {
    val (isExpanded, setExpanded) = rememberSaveable {
        mutableStateOf(false)
    }
    val (uidToDelete, setUidToDelete) = rememberSaveable {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxSize()){
        TopAppBar(
            title = { Text("Дневник") },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Book, "")
                }
            },
            actions = {
                IconButton(onClick = { viewModel.obtainEvent(DiaryEvent.OnEraseAllClicked) }) {
                    Icon(Icons.Default.DeleteSweep, "")
                }
            }
        )
        DiaryViewLayout(
            title = state.date,
            onNextClick = { viewModel.obtainEvent(DiaryEvent.NextDayClicked) },
            onPrevClick = { viewModel.obtainEvent(DiaryEvent.PreviousDayClicked)  }
        )
        {
            Box(Modifier.fillMaxSize()){
                Column {
                    DiariesView(
                        onDiaryClick = { uid -> viewModel.obtainEvent(DiaryEvent.OnDiaryClicked(uid)) },
                        state.items,
                        onLongTapCallback = {
                                uid ->
                            setExpanded(!isExpanded)
                            setUidToDelete(uid)
                        }
                    )
                }

                FloatingActionButton(
                    onClick = {
                        val uid = UUID.randomUUID().toString()
                        viewModel.obtainEvent(DiaryEvent.OnDiaryClicked(uid))
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(Icons.Rounded.Add, "", tint = Color.White)
                }

                if (isExpanded)
                    Dialog(
                        onDismissRequest = { setExpanded(false) }
                    ){
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                TextButton(onClick =
                                {
                                    setExpanded(false)
                                    viewModel.obtainEvent(DiaryEvent.OnDeleteClicked(uidToDelete))
                                    viewModel.obtainEvent(DiaryEvent.EnterScreen)
                                },

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                                ) {
                                    Text("Удалить")
                                }

                                TextButton(onClick = { setExpanded(false) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                                ) {
                                    Text("Пукнуть😀")
                                }

                                TextButton(onClick = { setExpanded(false) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                                ) {
                                    Text("Ничего не делать")
                                }
                            }
                        }
                    }
            }
        }
    }

}
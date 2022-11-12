package com.example.uksivtcompanion.screens.diary.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uksivtcompanion.data.entities.Diary
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.screens.components.DateSwitch
import com.example.uksivtcompanion.screens.diary.DetailsViewModel
import com.example.uksivtcompanion.screens.diary.models.DetailsEvent
import com.example.uksivtcompanion.screens.diary.models.DetailsViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(
    uid:String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onSaveClick: (item: Diary) -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    BackHandler(true) {
        onBackClick()
    }
    val viewState = viewModel.detailsViewState.observeAsState()

    when(val state = viewState.value){
        is DetailsViewState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center))
            }
        }
        is DetailsViewState.Display -> {
            val item: DiaryItem = state.diaryItem
            Column(
                Modifier
                    .padding(10.dp, 0.dp, 10.dp, 10.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                val isEditingTitle = rememberSaveable {
                    mutableStateOf(item.title.value == "")
                }
                TopAppBar(
                    title = {
                        if (isEditingTitle.value)
                            OutlinedTextField(
                                value = item.title.value,
                                onValueChange = { value -> item.title.value = value },
                                modifier = Modifier
                                    .height(60.dp),
                                singleLine = true,
                                label = { Text("Название") },
                                textStyle = TextStyle().copy(fontSize = 14.sp)
                            )
                        else
                            Text(
                                item.title.value,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                modifier = Modifier
                                    .height(25.dp),
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 17.sp
                            )
                    },
                    actions = {
                        IconButton(
                            onClick = { isEditingTitle.value = !isEditingTitle.value }
                        ){
                            if (isEditingTitle.value)
                                Icon(Icons.Default.Check, "")
                            else
                                Icon(Icons.Default.Edit, "")
                        }
                        IconButton(
                            onClick = {
                                if (item.title.value.isEmpty() && item.text.value.isEmpty())
                                    return@IconButton
                                onSaveClick(Diary(
                                    item.uid,
                                    item.title.value,
                                    item.text.value,
                                    item.date.value,
                                ))
                            }
                        ){
                            Icon(Icons.Default.Save, "")
                        }
                        IconButton(
                            onClick = {
                                onDeleteClick()
                            }
                        ){
                            Icon(Icons.Default.Delete, "")
                        }
                    },
                    navigationIcon = { IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "")
                    } }
                )
                DateSwitch(
                    title = item.date,
                    onPrevDayClick = {
                        viewModel.obtainEvent(DetailsEvent.PreviousDayClicked)
                    },
                    onNextDayClick = {
                        viewModel.obtainEvent(DetailsEvent.NextDayClicked)
                    }
                )

                Divider(Modifier.padding(5.dp))
                OutlinedTextField(value = item.text.value,
                    onValueChange = { value -> item.text.value = value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),//temp!! relayout later
                    maxLines = Int.MAX_VALUE,
                    placeholder = {
                        Text("Ваша заметка или задание...")
                    },
                    colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Blue)
                )
            }
        }
        else -> {}
    }

    LaunchedEffect(key1 = uid, block = {
        viewModel.obtainEvent(event = DetailsEvent.EnterScreen(uid))
    })

}
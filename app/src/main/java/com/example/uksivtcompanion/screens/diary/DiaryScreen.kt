package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoCell
import androidx.compose.material.icons.filled.NotAccessible
import androidx.compose.material.icons.outlined.NoBackpack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uksivtcompanion.screens.components.DateSwitch
import com.example.uksivtcompanion.screens.diary.models.DiaryEvent
import com.example.uksivtcompanion.screens.diary.models.DiaryViewState
import com.example.uksivtcompanion.screens.diary.views.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DiaryScreen(navController: NavController,
                viewModel: DiaryViewModel = hiltViewModel())
{
    val viewState = viewModel.diaryViewState.observeAsState()
    when(val state = viewState.value){
        DiaryViewState.Loading -> DiaryViewLoading()

        DiaryViewState.Error -> DiaryViewError()

        is DiaryViewState.NoItems -> {
            DiaryViewLayout(
                title = state.date,
                onNextClick = { viewModel.obtainEvent(DiaryEvent.NextDayClicked) },
                onPrevClick = { viewModel.obtainEvent(DiaryEvent.PreviousDayClicked)  }
            )
            {
                DiaryViewNoItems(
                    navController = navController,
                    OnDiaryClick = { uid -> viewModel.obtainEvent(DiaryEvent.OnDiaryClicked(uid)) }
                )
            }
        }

        is DiaryViewState.Display -> {
            DiaryViewDisplay(
                state = state,
                viewModel = viewModel,
                navController = navController
            )
        }
        else -> {}
    }

    LaunchedEffect(key1 = viewState, block = {
        viewModel.obtainEvent(event = DiaryEvent.EnterScreen)
    })


}

@Preview
@Composable
fun DialogPreview(){
    val (isExpanded, setExpanded) = rememberSaveable {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = { setExpanded(false) }
    ){
        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    Text("Удалить")
                }
                TextButton(onClick = { setExpanded(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    Text("Отмена")
                }
            }
        }
    }
}



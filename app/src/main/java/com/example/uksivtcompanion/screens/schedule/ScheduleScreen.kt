package com.example.uksivtcompanion.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.DialogNavigator
import com.example.uksivtcompanion.screens.schedule.models.ScheduleEvent
import com.example.uksivtcompanion.screens.schedule.models.ScheduleViewState
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewDisplay
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewEditing
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewLoading
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewNoData

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel,
                   navController: NavController
){
    val viewState = viewModel.scheduleViewState.observeAsState()
    val dayState = rememberSaveable{ mutableStateOf("") }
    when(val state = viewState.value){
        ScheduleViewState.Loading -> ScheduleViewLoading()
        ScheduleViewState.NoData -> ScheduleViewNoData(
            { viewModel.obtainEvent(event = ScheduleEvent.EditTimeSheet) },
            { viewModel.obtainEvent(event = ScheduleEvent.Import(it)) }
        )
        is ScheduleViewState.Editing ->
            ScheduleViewEditing(
                state.lessons,
                state.Day,
                { viewModel.obtainEvent(event = ScheduleEvent.EnterScreen) },
                { viewModel.obtainEvent(event = ScheduleEvent.UpdateTimeSheet(it)) }
            )
        is ScheduleViewState.Display -> {
            dayState.value = state.Day
            ScheduleViewDisplay(
                state.lessons,
                dayState,
                { viewModel.obtainEvent(event = ScheduleEvent.NextDayClicked) },
                { viewModel.obtainEvent(event = ScheduleEvent.PreviousDayClicked) },
                { viewModel.obtainEvent(event = ScheduleEvent.EditTimeSheet) },
                { viewModel.obtainEvent(event = ScheduleEvent.CreateClicked) },
                {
                    navController.navigate(
                        "Alert?m=Все записи удалены"
                    )
                    viewModel.obtainEvent(event = ScheduleEvent.DeleteAll)
                },
                { viewModel.obtainEvent(event = ScheduleEvent.Import(it)) },
                { viewModel.obtainEvent(event = ScheduleEvent.Export(it)) }
            )
        }
        else -> Text("Unmatched state")
    }
    LaunchedEffect(key1 = viewState, block = {
        viewModel.obtainEvent(event = ScheduleEvent.EnterScreen)
    })
}

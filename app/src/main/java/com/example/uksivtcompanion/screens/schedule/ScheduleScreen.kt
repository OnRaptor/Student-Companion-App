package com.example.uksivtcompanion.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uksivtcompanion.screens.schedule.models.ScheduleEvent
import com.example.uksivtcompanion.screens.schedule.models.ScheduleViewState
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewDisplay
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewLoading
import com.example.uksivtcompanion.screens.schedule.views.ScheduleViewNoData

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel){
    val viewState = viewModel.scheduleViewState.observeAsState()
    when(val state = viewState.value){
        ScheduleViewState.Loading -> ScheduleViewLoading()
        ScheduleViewState.NoData -> ScheduleViewNoData()
        is ScheduleViewState.Display -> ScheduleViewDisplay(viewModel)
        else -> Text("Unmatched state")
    }
    LaunchedEffect(key1 = viewState, block = {
        viewModel.obtainEvent(event = ScheduleEvent.EnterScreen)
    })
}

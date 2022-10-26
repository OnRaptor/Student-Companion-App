package com.example.uksivtcompanion.screens.diarydetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsEvent
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsViewState
import com.example.uksivtcompanion.screens.diarydetails.views.DetailsView

@Composable
fun DiaryDetailsScreen(uid:String,
                       detailsViewModel: DetailsViewModel,
                       navController:NavController
) {
    val viewState = detailsViewModel.detailsViewState.observeAsState()

    when(val state = viewState.value){
        is DetailsViewState.Loading -> {
           Box(Modifier.fillMaxSize()) {
               CircularProgressIndicator(
                   Modifier.align(Alignment.Center))
           }
        }
        is DetailsViewState.Display -> {
            DetailsView(
                item = state.diaryItem,
                onSaveClick = { detailsViewModel.obtainEvent(DetailsEvent.OnSaveClick) },
                onDeleteClick = {
                        detailsViewModel.obtainEvent(DetailsEvent.OnDeleteClick)
                        navController.navigate("diary")
                                },
                onNextDayClick = { detailsViewModel.obtainEvent(DetailsEvent.NextDayClicked) },
                onPrevDayClick = { detailsViewModel.obtainEvent(DetailsEvent.PreviousDayClicked) },
            )
        }
        else -> {}
    }


    LaunchedEffect(key1 = viewState, block = {
        detailsViewModel.obtainEvent(event = DetailsEvent.EnterScreen(uid = uid))
    })
    DisposableEffect(key1 = detailsViewModel) {
        onDispose {
            detailsViewModel.obtainEvent(DetailsEvent.OnDestroy)
        }
    }
}
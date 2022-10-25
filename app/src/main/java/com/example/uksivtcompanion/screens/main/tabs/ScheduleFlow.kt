package com.example.uksivtcompanion.screens.main.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.uksivtcompanion.screens.main.MainBottomScreen
import com.example.uksivtcompanion.screens.schedule.ScheduleScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.scheduleFlow(
    navController: NavController,
) {
    navigation(startDestination = "schedule", route = MainBottomScreen.Schedule.route) {
        composable("schedule") {
            ScheduleScreen(
            )
        }

    }
}
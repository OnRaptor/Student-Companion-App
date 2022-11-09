package com.example.uksivtcompanion.screens.main.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.uksivtcompanion.screens.main.MainBottomScreen
import com.example.uksivtcompanion.screens.schedule.ScheduleScreen
import com.example.uksivtcompanion.screens.schedule.ScheduleViewModel

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.scheduleFlow(
    navController: NavController
) {
    navigation(startDestination = "schedule", route = MainBottomScreen.Schedule.route) {
        composable("schedule") {
            ScheduleScreen(
                hiltViewModel(),
                navController
            )
        }

    }
}
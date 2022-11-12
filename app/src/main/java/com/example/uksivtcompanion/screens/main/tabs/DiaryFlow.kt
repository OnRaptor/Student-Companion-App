package com.example.uksivtcompanion.screens.main.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.uksivtcompanion.screens.diary.DiaryScreen
import com.example.uksivtcompanion.screens.diary.DiaryViewModel
import com.example.uksivtcompanion.screens.main.MainBottomScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.diaryFlow(
    navController: NavController,
) {
    navigation(startDestination = "diary", route = MainBottomScreen.Diary.route) {
        composable("diary") {
            val dailyViewModel = hiltViewModel<DiaryViewModel>()
            DiaryScreen(
                navController = navController, viewModel = dailyViewModel
            )
        }
    }
}
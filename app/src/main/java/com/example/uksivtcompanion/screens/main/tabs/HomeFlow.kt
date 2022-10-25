package com.example.uksivtcompanion.screens.main.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.uksivtcompanion.screens.home.HomeScreen
import com.example.uksivtcompanion.screens.main.MainBottomScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.homeFlow(
    navController: NavController,
) {
    navigation(startDestination = "home", route = MainBottomScreen.Home.route) {
        composable("home") {
            HomeScreen(
            )
        }

    }
}
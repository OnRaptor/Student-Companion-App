package com.example.uksivtcompanion.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uksivtcompanion.screens.main.tabs.diaryFlow
import com.example.uksivtcompanion.screens.main.tabs.homeFlow
import com.example.uksivtcompanion.screens.main.tabs.scheduleFlow

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController
) {
    val childNavController = rememberNavController()

    // Navigation Items
    val items = listOf(
        MainBottomScreen.Home,
        MainBottomScreen.Schedule,
        MainBottomScreen.Diary
    )

    Column {
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = childNavController,
                startDestination = MainBottomScreen.Home.route
            ) {
                homeFlow(childNavController)
                scheduleFlow(childNavController)
                diaryFlow(childNavController)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            NavigationBar {
                val navBackStackEntry by childNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val previousDestination = remember { mutableStateOf(items.first().route) }

                items.forEach { screen ->
                    val isSelected = currentDestination?.hierarchy
                        ?.any { it.route == screen.route } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (screen) {
                                    MainBottomScreen.Home -> Icons.Filled.Home
                                    MainBottomScreen.Schedule -> Icons.Filled.Schedule
                                    MainBottomScreen.Diary -> Icons.Filled.Book
                                },
                                contentDescription = null)
                        },
                        label = {
                            Text(
                                stringResource(id = screen.resourceId),
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            if (screen.route == previousDestination.value) return@NavigationBarItem
                            previousDestination.value = screen.route

                            childNavController.navigate(screen.route) {
                                popUpTo(childNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        }
    }
}
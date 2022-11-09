package com.example.uksivtcompanion.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.uksivtcompanion.screens.home.SettingsDialog
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
                dialog("Alert?m={message}", dialogProperties = DialogProperties(true, true)){
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(10.dp, 0.dp, 10.dp, 0.dp),
                        shape = ShapeDefaults.ExtraLarge,
                        elevation = CardDefaults.elevatedCardElevation()
                    ) {
                        Box(Modifier.fillMaxSize()){
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Alert",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Red
                                )
                                Divider()
                                Text(text = it.arguments?.getString("message")!!)
                            }
                            TextButton(
                                onClick = {navController.popBackStack()},
                                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                            ) {
                                Text("OK")
                            }
                        }
                    }
                }

                dialog("Settings", dialogProperties = DialogProperties(true, true)){
                    SettingsDialog(navController)
                }
                dialog("Import"){
                    Card(Modifier.fillMaxWidth()){
                        Text("Import is not created yet", modifier = Modifier.padding(10.dp))
                    }
                }
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
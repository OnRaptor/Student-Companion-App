package com.example.uksivtcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.uksivtcompanion.screens.diary.DiaryDetailsScreen
import com.example.uksivtcompanion.screens.diary.DiaryScreen
import com.example.uksivtcompanion.screens.home.HomeScreen
import com.example.uksivtcompanion.screens.schedule.ScheduleScreen
import com.example.uksivtcompanion.ui.theme.UksivtCompanionTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon : ImageVector) {
        object Home : Screen("profile", R.string.home, Icons.Filled.Home)
        object Schedule : Screen("schedule", R.string.schedule, Icons.Filled.List)
        object Diary : Screen("diary", R.string.diary, Icons.Filled.Edit)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screens = listOf(Screen.Home,Screen.Schedule, Screen.Diary)
        setContent {
            UksivtCompanionTheme {
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
                Scaffold(
                    topBar = {
                             TopAppBar(title = { Text("Uksivt Companion") },
                                 actions = {
                                     var expanded by remember { mutableStateOf(false) }
                                     Box{
                                         IconButton(onClick = { expanded = !expanded }) {
                                             Icon(Icons.Rounded.MoreVert, contentDescription = null)
                                         }
                                         DropdownMenu(expanded = expanded,
                                             onDismissRequest = { expanded = false },
                                             offset = DpOffset(10.dp, 0.dp)
                                         ) {
                                            Text("Настройки",
                                                fontSize = 18.sp,
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .clickable {
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar("Settings clicked, but ui didn't created yet(((", "I understand")
                                                        }
                                                        expanded = false
                                                    }
                                                )
                                         }
                                     }

                                 }
                             )
                    },
                    bottomBar = {
                        NavigationBar() {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screens.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
                        composable(Screen.Home.route) { HomeScreen() }
                        composable(Screen.Schedule.route) { ScheduleScreen() }
                        composable(Screen.Diary.route) { DiaryScreen(navController) }
                        composable("diary-details") { DiaryDetailsScreen() }
                    }
                }
            }
        }
    }
}


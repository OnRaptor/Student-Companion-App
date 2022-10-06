package com.example.uksivtcompanion

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.uksivtcompanion.ui.theme.UksivtCompanionTheme



class MainActivity : ComponentActivity() {

    sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon : ImageVector) {
        object Home : Screen("profile", R.string.home, Icons.Filled.Home)
        object Schedule : Screen("friendslist", R.string.schedule, Icons.Filled.List)
        object Diary : Screen("friendslist", R.string.diary, Icons.Filled.Edit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screens = listOf(Screen.Home,Screen.Schedule, Screen.Diary)
        setContent {
            UksivtCompanionTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                             TopAppBar(title = { Text("Uksivt Companion") },
                                 actions = {
                                     IconButton(onClick = { /*TODO*/ }) {
                                         Icon(Icons.Rounded.Settings, contentDescription = null)
                                     }
                                 }
                             )
                    },
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screens.forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
                        composable(Screen.Home.route) { }
                        composable(Screen.Schedule.route) { }
                        composable(Screen.Diary.route) { }
                    }
                }
            }
        }
    }
}


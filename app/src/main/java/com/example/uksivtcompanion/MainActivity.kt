package com.example.uksivtcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uksivtcompanion.screens.main.MainScreen
import com.example.uksivtcompanion.ui.theme.UksivtCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UksivtCompanionTheme() {
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                Surface {
                    NavHost(navController = navController, startDestination = "main") {
                        /*composable("splash") {
                            SplashScreen(navController = navController)
                        }*/

                        composable("main") {
                            MainScreen(navController = navController)
                        }

                        /*composable("compose") {
                            val composeViewModel = hiltViewModel<ComposeViewModel>()
                            ComposeScreen(
                                navController = navController,
                                composeViewModel = composeViewModel
                            )
                        }*/
                    }
                }
            }
        }
    }
}


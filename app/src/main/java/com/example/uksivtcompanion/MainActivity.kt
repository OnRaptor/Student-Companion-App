package com.example.uksivtcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.uksivtcompanion.screens.components.AlertCard
import com.example.uksivtcompanion.screens.main.MainScreen
import com.example.uksivtcompanion.ui.theme.UksivtCompanionTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UksivtCompanionTheme(false){
                val navController = rememberNavController()
                Surface {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            Box(modifier = Modifier.fillMaxSize()){
                                Image(painter = painterResource(R.drawable.based),
                                    contentDescription = "",
                                    Modifier.align(Alignment.TopCenter).height(300.dp).width(300.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                                Column(Modifier.align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator()
                                    Text("Загрузочка...")
                                }
                                LaunchedEffect(key1 = true, block = {
                                    delay(3000L)
                                    navController.navigate("main")
                                })
                            }
                        }
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        dialog("Alert?m={message}", dialogProperties = DialogProperties(true, true)){
                            AlertCard(message = it.arguments?.getString("message")!!) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}


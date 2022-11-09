package com.example.uksivtcompanion

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
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
                                LaunchedEffect(key1 = Unit, block = {
                                    navController.navigate("main")
                                })
                            }
                        }
                        composable("main") {
                            MainScreen(navController = navController)
                        }
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.BottomCenter)
                                    ) {
                                        Text("OK")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


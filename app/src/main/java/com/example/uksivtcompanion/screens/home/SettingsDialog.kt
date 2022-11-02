package com.example.uksivtcompanion.screens.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.uksivtcompanion.BuildConfig

@Composable
fun SettingsDialog(onDismiss:(bool:Boolean) -> Unit) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismiss(false) }
    ){
        Card(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Box{
                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                ) {

                    Text("Настройки(Fake)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    SwitchText("Auto Fetch")
                    SwitchText("Dev setting")
                    SwitchText("Async Mode", true)
                    SwitchText("Dev setting 2")

                    TextButton(modifier = Modifier.fillMaxWidth(),onClick = { /*TODO*/ }) {
                        Text("Изменить url сервера")
                    }
                    TextButton(modifier = Modifier.fillMaxWidth(),onClick = { /*TODO*/ }) {
                        Text("Авторизация в Google")
                    }
                    TextButton(modifier = Modifier.fillMaxWidth(),onClick = { /*TODO*/ }) {
                        Text("Тестовый запрос на получение замен")
                    }
                    TextButton(modifier = Modifier.fillMaxWidth(),onClick = { /*TODO*/ }) {
                        Text("Изменить расписание")
                    }
                    Divider(Modifier.padding(4.dp))
                    Spacer(Modifier.height(20.dp))
                }
                /*OutlinedButton(
                    onClick = { onDismiss(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                        .align(Alignment.BottomCenter),
                ) {
                    Text("OK")
                }*/

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        "Версия ${BuildConfig.VERSION_NAME}"
                    )
                    Row{
                        Icon(Icons.Outlined.MonetizationOn,"")
                        Text(
                            "Github",
                            modifier = Modifier.padding(3.dp).clickable {
                                ContextCompat.startActivity(
                                    context,
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/OnRaptor/UksivtCompanion")
                                    ),
                                    null
                                )
                            },
                            textDecoration = TextDecoration.Underline
                        )
                    }

                }
            }

        }
    }
}

@Composable
fun SwitchText(text:String, isChecked:Boolean = false){
    val (isCheckedValue, setChecked) = remember {
        mutableStateOf(isChecked)
    }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text)
        Switch(
            checked = isCheckedValue,
            onCheckedChange = setChecked,
        )
    }
}

@Composable
fun RadioText(text:String, isChecked:Boolean = false){
    val (isCheckedValue, setChecked) = remember {
        mutableStateOf(isChecked)
    }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text)
        RadioButton(
            selected = isCheckedValue,
            onClick  = { setChecked(!isCheckedValue) },
        )
    }
}
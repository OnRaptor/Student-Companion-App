package com.example.uksivtcompanion.screens.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uksivtcompanion.R
import com.example.uksivtcompanion.screens.components.DateSwitchInline


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val wise_mystical_tree = ImageView(LocalContext.current)
    wise_mystical_tree.setImageResource(R.drawable.wise)

    val EasterEgg = AlertDialog.Builder(LocalContext.current)
        .setTitle("Пасхалка")
        .setPositiveButton("Ашалеть", null)
        .setView(wise_mystical_tree)
        .create()

    Column {
        TopAppBar(
            title = { Text("Student Companion") },
            navigationIcon = {
                IconButton(onClick = {
                    EasterEgg.show()
            }) {
                Icon(Icons.Default.Home, contentDescription = "")
            } },
            actions = {
                IconButton(onClick = { navController.navigate("Settings") }) {
                    Icon(Icons.Filled.Settings,"")
                }
            }
        )
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            //Spacer(modifier = Modifier.height(25.dp))
            ScheduleWidget()
            //Spacer(modifier = Modifier.height(25.dp))
            DiaryWidget()
            //Spacer(modifier = Modifier.height(25.dp))
            ReplacesWidget()
            //Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun ScheduleWidget(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable { },
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            DateSwitchInline(Title = stringResource(id = R.string.schedule_widget))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text("Русский")
                Text("9:30")
            }
            Divider()
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                Text("Русский")
                Text("11:30")
            }
            Divider()

            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                Text("Русский")
                Text("13:30")
            }
        }
    }
}

@Composable
fun DiaryWidget(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable { },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            DateSwitchInline(Title = stringResource(id = R.string.diary_widget))

            DiaryWriteItem()
            Divider()
            DiaryWriteItem()
            Divider()
            DiaryWriteItem()
            Divider()
            DiaryWriteItem()
            Divider()
            DiaryWriteItem()

        }
    }
}

@Composable
fun ReplacesWidget(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable { },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            DateSwitchInline(Title = stringResource(id = R.string.replaces_widget))

            ReplaceableItem()
            Divider()
            ReplaceableItem()
            Divider()
            ReplaceableItem()
            Divider()
            ReplaceableItem()

        }
    }
}

@Composable
fun ReplaceableItem(){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        Row{
            Text("Русский", textDecoration = TextDecoration.LineThrough)
            Text(" -> ")
            Text("Математика")
        }
        Text("9:30")
    }
}

@Composable
fun DiaryWriteItem(){
    Row(Modifier.padding(5.dp)){
        var isExpanded: Boolean by remember { mutableStateOf(false)}
        Text("Математика: ", modifier = Modifier.width(100.dp), maxLines = 1)
        Text("Сделать 120 номер + подготовиться к кр по дифф уравнениям",
            maxLines = if (isExpanded) 4 else 1,
            modifier = Modifier.clickable { isExpanded = !isExpanded },
            overflow = TextOverflow.Ellipsis
        )
    }
}
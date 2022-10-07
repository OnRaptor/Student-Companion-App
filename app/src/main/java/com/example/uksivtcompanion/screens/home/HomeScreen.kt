package com.example.uksivtcompanion.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.R
import java.lang.Float

@Composable
fun HomeScreen(){
    Column(modifier = Modifier
        .padding(8.dp)
        .verticalScroll(rememberScrollState())
        .fillMaxHeight()){
        Spacer(modifier = Modifier.height(25.dp))
        ScheduleWidget()
        Spacer(modifier = Modifier.height(25.dp))
        DiaryWidget()
        Spacer(modifier = Modifier.height(25.dp))
        ReplacesWidget()
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun ScheduleWidget(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        elevation = 10.dp
        ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null)
                }
                Text(text = stringResource(id = R.string.schedule_widget),
                    fontSize = 22.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
            Divider()
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text("Русский")
                Text("9:30")
            }
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                Text("Русский")
                Text("11:30")
            }
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
        .padding(10.dp)
        .clickable { },
        elevation = 10.dp
    ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null)
                }
                Text(text = stringResource(id = R.string.diary_widget),
                    fontSize = 22.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
            Divider()

            DiaryWriteItem()
            DiaryWriteItem()
            DiaryWriteItem()
            DiaryWriteItem()
            DiaryWriteItem()

        }
    }
}

@Composable
fun ReplacesWidget(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        elevation = 10.dp
    ) {
        Column(Modifier.padding(8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null)
                }
                Text(text = stringResource(id = R.string.replaces_widget),
                    fontSize = 22.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
            Divider()
            ReplaceableItem()
            ReplaceableItem()
            ReplaceableItem()
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
        Text("Пукнуть пукнуть пакакть Пукнуть пукнуть пакакть Пукнуть пукнуть пакакть Пукнуть пукнуть пакакть Пукнуть пукнуть пакакть",
            maxLines = if (isExpanded) 4 else 1,
            modifier = Modifier.clickable { isExpanded = !isExpanded },

        )
    }
}
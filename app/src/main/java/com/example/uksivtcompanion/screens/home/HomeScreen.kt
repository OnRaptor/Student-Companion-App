package com.example.uksivtcompanion.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.R


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
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        shadowElevation = 10.dp,
        tonalElevation = 10.dp
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
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        shadowElevation = 10.dp,
        tonalElevation = 10.dp

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
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        shadowElevation = 10.dp,
        tonalElevation = 10.dp
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
        Text("Сделать 120 номер + подготовиться к кр по дифф уравнениям",
            maxLines = if (isExpanded) 4 else 1,
            modifier = Modifier.clickable { isExpanded = !isExpanded },
            overflow = TextOverflow.Ellipsis
        )
    }
}
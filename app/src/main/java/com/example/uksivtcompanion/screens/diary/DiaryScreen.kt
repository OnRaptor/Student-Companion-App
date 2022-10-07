package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.R

@Composable
fun DiaryScreen(){
    Column(Modifier.verticalScroll(rememberScrollState())
    ){
        DateSwitch()
        DiariesView()
    }
}

@Composable
fun DateSwitch(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { },
        elevation = 10.dp
    ){
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
    }
}

@Composable
fun DiaryView(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        ,elevation = 10.dp
    ){
        val (text, setText) = rememberSaveable {
            mutableStateOf("")
        }

        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Text(modifier= Modifier.padding(4.dp), text = "Математика")
            OutlinedTextField(value = text
                ,onValueChange = setText
                ,maxLines = 3
                ,modifier = Modifier
                    .fillMaxWidth())
        }
    }
}

@Composable
fun DiariesView(){
    DiaryView()
    DiaryView()
    DiaryView()
    DiaryView()
}
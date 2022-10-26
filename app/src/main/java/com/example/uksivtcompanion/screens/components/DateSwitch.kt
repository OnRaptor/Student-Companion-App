package com.example.uksivtcompanion.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DateSwitch(
    title: MutableState<String> = mutableStateOf(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))
){
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp, 7.dp, 18.dp, 0.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
            IconButton(onClick = {
                title.value = LocalDate.parse(title.value, dateFormatter)
                .minusDays(1).format(dateFormatter)
            }) {
                Icon(Icons.Filled.ArrowLeft, contentDescription = null)
            }
            Text(text = title.value,
                fontSize = 22.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                title.value = LocalDate.parse(title.value, dateFormatter)
                    .plusDays(1).format(dateFormatter)
            }) {
                Icon(Icons.Filled.ArrowRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun DateSwitch(
    title: MutableState<String>,
    onPrevDayClick: () -> Unit,
    onNextDayClick: () -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp, 7.dp, 18.dp, 0.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
            IconButton(onClick = onPrevDayClick) {
                Icon(Icons.Filled.ArrowLeft, contentDescription = null)
            }
            Text(text = title.value,
                fontSize = 22.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onNextDayClick) {
                Icon(Icons.Filled.ArrowRight, contentDescription = null)
            }
        }
    }
}

@Composable
fun DateSwitchInline(Title: String){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ArrowLeft, contentDescription = null)
        }
        Text(text = Title,
            fontSize = 22.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ArrowRight, contentDescription = null)
        }
    }
}
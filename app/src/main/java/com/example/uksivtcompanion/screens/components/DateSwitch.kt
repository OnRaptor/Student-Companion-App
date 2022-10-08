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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.R

@Composable
fun DateSwitch(Title: String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
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
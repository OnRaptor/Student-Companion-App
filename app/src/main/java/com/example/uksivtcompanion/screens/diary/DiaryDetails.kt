package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailsScreen(id:String) {
    val (text, setText) = rememberSaveable {
        mutableStateOf("Id of diary -> $id")
    }
    Column(Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Математика 12.02.22",
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
        Divider(Modifier.padding(5.dp))
        OutlinedTextField(value = text,
            onValueChange = setText,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),//temp!! relayout later
            maxLines = Int.MAX_VALUE,
            placeholder = { Text("Ваша заметка или задание...")
            }
        )

        Row (horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(5.dp)
        ){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.PriorityHigh, "Mark")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Сохранить")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, "Delete")
            }
        }
    }
}
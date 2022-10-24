package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uksivtcompanion.screens.components.DateSwitch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailsScreen(item:DiaryItem,
                       diaryViewModel: DiaryViewModel = hiltViewModel(),
                       navController:NavController
) {
    val isEditingTitle = rememberSaveable() {
        mutableStateOf(item.title.value == "")
    }
    Column(
        Modifier
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        DateSwitch(
            title = item.date
        )
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            if(isEditingTitle.value)
                OutlinedTextField(
                    value = item.title.value,
                    onValueChange = { value -> item.title.value = value },
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(.9f),
                    singleLine = true,
                    label = { Text("Название") }
                )
            else
            Text(
                item.title.value,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth(.9f),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp
            )

            IconButton(onClick = { isEditingTitle.value = !isEditingTitle.value }) {
                if (!isEditingTitle.value)
                    Icon(Icons.Filled.Edit, "Edit")
                else
                    Icon(Icons.Filled.Check, "Save")
            }
        }
        
        Divider(Modifier.padding(5.dp))
        OutlinedTextField(value = item.text.value,
            onValueChange = { value -> item.text.value = value },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),//temp!! relayout later
            maxLines = Int.MAX_VALUE,
            placeholder = {
                Text("Ваша заметка или задание...")
            },
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Blue)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            IconButton(onClick = {
                diaryViewModel.setCurrentItem(item)
                diaryViewModel.markDiaryFunc()
            }) {
                Icon(Icons.Filled.PriorityHigh, "Mark")
            }
            Button(onClick = {
                diaryViewModel.setCurrentItem(item)
                diaryViewModel.saveDiaryFunc()
            }) {
                Text("Сохранить", color = Color.White)
            }
            IconButton(onClick = {
                diaryViewModel.setCurrentItem(item)
                diaryViewModel.deleteDiaryFunc()
                navController.navigate("diary")
            }) {
                Icon(Icons.Filled.Delete, "Delete")
            }
        }
    }
}
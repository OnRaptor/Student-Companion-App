package com.example.uksivtcompanion.screens.diary

import android.content.res.Resources.Theme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.uksivtcompanion.ui.theme.UksivtCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailsScreen(uid:String,
                       diaryViewModel: DiaryViewModel = hiltViewModel()
) {
    val item = diaryViewModel.getItemByUID(uid)
    val (text, setText) = rememberSaveable {
        mutableStateOf(item.text)
    }
    val isEditingTitle = rememberSaveable() {
        mutableStateOf(false)
    }
    val (title, setTitle) = rememberSaveable() {
        mutableStateOf(item.title)
    }
    Column(
        Modifier
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically){
            if (!isEditingTitle.value)
                Text(
                    title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier.height(20.dp).fillMaxWidth(.9f),
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp
                    )
            else
                OutlinedTextField(
                    value = title,
                    onValueChange = setTitle,
                    modifier = Modifier.height(60.dp).fillMaxWidth(.9f),
                    singleLine = true,
                    label = { Text("Название") }
                )

            IconButton(onClick = { isEditingTitle.value = !isEditingTitle.value }) {
                if (!isEditingTitle.value)
                    Icon(Icons.Filled.Edit, "Edit")
                else
                    Icon(Icons.Filled.Check, "Save")
            }
        }

        Text(
            item.data,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Divider(Modifier.padding(5.dp))
        OutlinedTextField(value = text,
            onValueChange = setText,
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
            IconButton(onClick = { diaryViewModel.markDiaryFunc() }) {
                Icon(Icons.Filled.PriorityHigh, "Mark")
            }
            Button(onClick = { diaryViewModel.saveDiaryFunc() }) {
                Text("Сохранить", color = Color.White)
            }
            IconButton(onClick = { diaryViewModel.deleteDiaryFunc() }) {
                Icon(Icons.Filled.Delete, "Delete")
            }
        }
    }
}
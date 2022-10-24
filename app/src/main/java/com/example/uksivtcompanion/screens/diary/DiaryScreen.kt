package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uksivtcompanion.R
import com.example.uksivtcompanion.screens.components.DateSwitch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Composable
fun DiaryScreen(navController: NavController,
                viewModel: DiaryViewModel = hiltViewModel()){
    val date = rememberSaveable{ mutableStateOf(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))}

    Box(Modifier.fillMaxSize()){
        Column(Modifier.verticalScroll(rememberScrollState())
        ){
            DateSwitch(
                date
            )
            DiariesView(navController, viewModel.getPreviewOfDiaries(date.value))
            Spacer(modifier = Modifier.height(25.dp))
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            FloatingActionButton(
                onClick = { viewModel.createDiaryFunc(date.value) },
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(Icons.Rounded.Add, "", tint = Color.White)
            }
        }

    }
    viewModel.onDiaryCreateCallback = { navController.navigate("diary-details/$it") }
}

@Composable
fun DiaryView(navController: NavController,
              item:DiaryItemPreview
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable
        {
            navController.navigate("diary-details/" + item.uid)
        },
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Column(Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Text(modifier= Modifier.padding(4.dp),
                text = item.title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(text = item.description,
                modifier = Modifier.fillMaxWidth(2f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@Composable
fun DiariesView(navController: NavController,
                diaries:List<DiaryItemPreview>
){
    diaries.map { item ->
        DiaryView(navController = navController, item = item)
     }
}
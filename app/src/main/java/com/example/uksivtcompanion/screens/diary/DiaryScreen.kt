package com.example.uksivtcompanion.screens.diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uksivtcompanion.R
import com.example.uksivtcompanion.screens.components.DateSwitch

@Composable
fun DiaryScreen(navController: NavController,
                viewModel: DiaryViewModel){
    Column(Modifier.verticalScroll(rememberScrollState())
    ){
        DateSwitch(stringResource(id = R.string.diary_widget))
        DiariesView(navController, viewModel.getPreviewOfDiaries())
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun DiaryView(navController: NavController,
              item:DiaryItemPreview
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable { navController.navigate("diary-details") },
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Column(Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Text(modifier= Modifier.padding(4.dp),
                text = item.title,
                fontWeight = FontWeight.Bold
            )
            Text(text = item.description,
                modifier = Modifier.fillMaxWidth(2f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
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
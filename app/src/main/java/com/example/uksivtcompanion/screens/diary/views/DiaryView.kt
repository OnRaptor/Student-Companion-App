package com.example.uksivtcompanion.screens.diary.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uksivtcompanion.data.entities.DiaryItem

@Composable
fun DiaryView(
    navController: NavController,
    item: DiaryItem
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clickable
        {
            navController.navigate("details/" + item.uid)
        },
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Text(modifier= Modifier.padding(4.dp),
                text = item.title.value,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(text = item.text.value,
                modifier = Modifier.fillMaxWidth(2f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}
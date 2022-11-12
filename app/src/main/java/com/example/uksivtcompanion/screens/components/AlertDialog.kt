package com.example.uksivtcompanion.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertCard(
    message:String,
    onConfirm:() -> Unit
){
    Card(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp, 0.dp, 10.dp, 0.dp),
        shape = ShapeDefaults.ExtraLarge,
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Box(Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Alert",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red
                )
                Divider()
                Text(text = message)
            }
            TextButton(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
            ) {
                Text("OK")
            }
        }
    }
}
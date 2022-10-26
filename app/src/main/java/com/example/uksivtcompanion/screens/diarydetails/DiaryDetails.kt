package com.example.uksivtcompanion.screens.diarydetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.screens.components.DateSwitch
import com.example.uksivtcompanion.screens.diarydetails.DetailsViewModel
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsEvent
import com.example.uksivtcompanion.screens.diarydetails.models.DetailsViewState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailsScreen(uid:String = UUID.randomUUID().toString(),
                       detailsViewModel: DetailsViewModel,
                       navController:NavController
) {
    val item = DiaryItem(
        uid,
        remember{mutableStateOf("")},
        remember{mutableStateOf("")},
        remember{mutableStateOf(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))}
    )
    val viewState = detailsViewModel.detailsViewState.observeAsState()

    when(val state = viewState.value){
        is DetailsViewState.Loading -> {
           Box() {
               CircularProgressIndicator(
                   Modifier
                       .align(Alignment.Center)
                       .fillMaxSize())
           }
        }
        is DetailsViewState.Display -> {
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

                    if (isEditingTitle.value)
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
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { detailsViewModel.obtainEvent(DetailsEvent.OnSaveClick(item)) }) {
                    Text("Apply")
                }
            }
        }
    }


    LaunchedEffect(key1 = viewState, block = {
        detailsViewModel.obtainEvent(event = DetailsEvent.EnterScreen)
    })

}
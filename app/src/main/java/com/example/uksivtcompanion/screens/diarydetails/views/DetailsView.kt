package com.example.uksivtcompanion.screens.diarydetails.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uksivtcompanion.data.entities.DiaryItem
import com.example.uksivtcompanion.screens.components.DateSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(
    item:DiaryItem,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPrevDayClick: () -> Unit,
    onNextDayClick: () -> Unit
) {
    Column(
        Modifier
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val isEditingTitle = rememberSaveable() {
            mutableStateOf(item.title.value == "")
        }
        TopAppBar(
            title = {
                    if (isEditingTitle.value)
                        OutlinedTextField(
                            value = item.title.value,
                            onValueChange = { value -> item.title.value = value },
                            modifier = Modifier
                                .height(60.dp),
                            singleLine = true,
                            label = { Text("Название") },
                            textStyle = TextStyle().copy(fontSize = 14.sp)
                )
                    else
                        Text(
                            item.title.value,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .height(25.dp),
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 17.sp
                        )
            },
            actions = {
                val (isExpanded, setExpanded) = rememberSaveable {
                    mutableStateOf(false)
                }
                IconButton(onClick = { setExpanded(!isExpanded) }) {
                    Icon(Icons.Filled.MoreVert, "Settings")
                }
                DropdownMenu(expanded = isExpanded, onDismissRequest = { setExpanded(!isExpanded) }) {
                    DropdownMenuItem(
                        text = { Text(
                            if (isEditingTitle.value)
                            "Применить название"
                            else "Изменить название") },
                        onClick = { isEditingTitle.value = !isEditingTitle.value }
                    )
                    DropdownMenuItem(
                        text = { Text("Сохранить") },
                        onClick = {
                            if (item.title.value.isEmpty() && item.text.value.isEmpty())
                                return@DropdownMenuItem

                            onSaveClick()
                            setExpanded(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Удалить") },
                        onClick = onDeleteClick
                    )
                }
            }
        )
        DateSwitch(
            title = item.date,
            onPrevDayClick = onPrevDayClick,
            onNextDayClick = onNextDayClick
        )

        Divider(Modifier.padding(5.dp))
        OutlinedTextField(value = item.text.value,
            onValueChange = { value -> item.text.value = value },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),//temp!! relayout later
            maxLines = Int.MAX_VALUE,
            placeholder = {
                Text("Ваша заметка или задание...")
            },
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Blue)
        )


    }
}
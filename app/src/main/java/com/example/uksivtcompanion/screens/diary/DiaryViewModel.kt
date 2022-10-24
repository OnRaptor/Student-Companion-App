package com.example.uksivtcompanion.screens.diary

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(

) : ViewModel(){
    private var items:MutableList<DiaryItem> = mutableListOf(
        DiaryItem(
            UUID.randomUUID().toString(),
            mutableStateOf("Тестовый предмет"),
            mutableStateOf("Предмет описания"),
            mutableStateOf("24.10.2022")))

    private lateinit var currentItem:DiaryItem

    var onDiaryCreateCallback:(id:String) -> Unit = {}

    val saveDiaryFunc:() -> Unit = {
        items[items.indexOf(items.find { it.uid == currentItem.uid })] = currentItem
    }

    val deleteDiaryFunc:() -> Unit = {
        items.remove(items.find{it.uid == currentItem.uid})
    }

    val markDiaryFunc:() -> Unit = {

    }

    fun setCurrentItem(item:DiaryItem)  {
        currentItem = item
    }

    fun getPreviewOfDiaries(date:String =
                                SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date()))
    : List<DiaryItemPreview>{
        //retrieve data from file
        return items.mapNotNull { item ->
            if(item.date.value == date)
            DiaryItemPreview(
            item.uid,
            item.title.value,
            item.text.value
            )
            else null
        }
    }

    fun getItemByUID(uid:String):DiaryItem {
        try {
            return items.first { item -> item.uid == uid }
        }
        catch(e:NoSuchElementException){
            return DiaryItem("", mutableStateOf(""),mutableStateOf(""),mutableStateOf(""))
        }
    }



    fun createDiaryFunc(date: String):DiaryItem{
        val item = DiaryItem(
            UUID.randomUUID().toString(),
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(date))
        items.add(item)
        onDiaryCreateCallback(item.uid)
        return item
    }
}

data class DiaryItemPreview(val uid: String,val title:String, val description:String)
data class DiaryItem(val uid:String, var title:MutableState<String>, var text:MutableState<String>,
                     var date:MutableState<String>)
package com.example.uksivtcompanion.screens.diary

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
            "Тестовый предмет",
            "Предмет описания",
            "12.02.2020"))

    private var currentItem:DiaryItem = DiaryItem("", "", "", "")

    var onDiaryCreateCallback:(id:String) -> Unit = {}

    val saveDiaryFunc:() -> Unit = {
        items.set(items.indexOf(items.find { it.uid == currentItem.uid }), currentItem)
    }

    val deleteDiaryFunc:() -> Unit = {
        items.remove(items.find{it.uid == currentItem.uid})
    }

    val markDiaryFunc:() -> Unit = {

    }

    fun setCurrentItem(item:DiaryItem)  {
        currentItem = item
    }

    fun getPreviewOfDiaries() : List<DiaryItemPreview>{
        //retrieve data from file
        return items.map { item -> DiaryItemPreview(
            item.uid,
            item.title,
            if (item.text == "") "Пусто" else item.text.subSequence(0, 15).toString())
        }
    }
    fun getItemByUID(uid:String):DiaryItem {
        return items.first { item -> item.uid == uid }
    }



    fun createDiaryFunc():DiaryItem{
        val item = DiaryItem(
            UUID.randomUUID().toString(),
            "",
            "",
            SimpleDateFormat("dd/M/yyyy", Locale.US).format(Date()))
        items.add(item)
        onDiaryCreateCallback(item.uid)
        return item
    }
}

data class DiaryItemPreview(val uid: String,val title:String, val description:String)
data class DiaryItem(val uid:String,val title:String, val text:String, val data:String)
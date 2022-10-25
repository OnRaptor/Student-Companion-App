package com.example.uksivtcompanion.screens.main

import androidx.annotation.StringRes
import com.example.uksivtcompanion.R

sealed class MainBottomScreen(val route: String, @StringRes val resourceId: Int) {
    object Home : MainBottomScreen("homeFlow", R.string.title_home)
    object Schedule : MainBottomScreen("scheduleFlow", R.string.title_schedule)
    object Diary : MainBottomScreen("diaryFlow", R.string.title_diary)
}
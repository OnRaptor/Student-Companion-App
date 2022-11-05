package com.example.uksivtcompanion.data.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.uksivtcompanion.data.entities.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOError

public class ScheduleDAO(private val gson: Gson, private val context : Context) {
    private val FILENAME : String = "schedule.json"
    suspend fun getSchedule() : Schedule?{
        try {
            var parsedData : Schedule
            withContext(Dispatchers.IO) {
                val reader = context.openFileInput(FILENAME).reader()
                val json: String = reader.readText()
                reader.close()
                parsedData = gson.fromJson(json, object : TypeToken<Schedule>() {}.type )
            }
            return parsedData
        }
        catch (_: IOError){}
        return null
    }

    suspend fun setSchedule(schedule: Schedule){
        try {
            withContext(Dispatchers.IO) {
                context.openFileOutput(FILENAME, MODE_PRIVATE).writer().write(gson.toJson(schedule))
            }
        }
        catch (_: IOError){}
    }
}













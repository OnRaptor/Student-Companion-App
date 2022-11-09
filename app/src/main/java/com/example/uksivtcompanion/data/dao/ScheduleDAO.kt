package com.example.uksivtcompanion.data.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.uksivtcompanion.data.entities.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOError

class ScheduleDAO(private val gson: Gson, private val context : Context) {
    private val FILENAME : String = "schedule.json"

    suspend fun getSchedule() : Schedule{
        var parsedData : Schedule = Schedule()
        try {
            withContext(Dispatchers.IO) {
                val reader = JsonReader(context.openFileInput(FILENAME).reader())
                reader.isLenient = true
                parsedData = gson.fromJson(reader, object : TypeToken<Schedule?>() {}.type)
            }
            return parsedData
        }
        catch (_: java.io.FileNotFoundException){
            withContext(Dispatchers.IO) {
                val writer = context.openFileOutput(FILENAME, MODE_PRIVATE).writer()
                writer.write(gson.toJson(Schedule()))
                writer.close()
            }
        }
        return parsedData
    }

    suspend fun setSchedule(schedule: Schedule){
        try {
            withContext(Dispatchers.IO) {
                val writer = context.openFileOutput(FILENAME, MODE_PRIVATE).writer()
                writer.write(gson.toJson(schedule))
                writer.close()
            }
        }
        catch (_: IOError){}
    }
}













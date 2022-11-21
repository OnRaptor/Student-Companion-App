package com.example.uksivtcompanion.data.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.uksivtcompanion.data.entities.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import java.io.IOError


class ScheduleDAO(private val gson: Gson, public val context : Context) {
    private val filename : String = "schedule.json"



    suspend fun getSchedule() : Schedule{
        var parsedData : Schedule = Schedule()
        try {
            withContext(Dispatchers.IO) {
                val reader = JsonReader(context.openFileInput(filename).reader())
                reader.isLenient = true
                parsedData = gson.fromJson(reader, object : TypeToken<Schedule?>() {}.type)
            }
            return parsedData
        }
        catch (_: java.io.FileNotFoundException){
            withContext(Dispatchers.IO) {
                val writer = context.openFileOutput(filename, MODE_PRIVATE).writer()
                writer.write(gson.toJson(Schedule()))
                writer.close()
            }
        }
        return parsedData
    }

    suspend fun setSchedule(schedule: Schedule){
        try {
            withContext(Dispatchers.IO) {
                val writer = context.openFileOutput(filename, MODE_PRIVATE).writer()
                writer.write(gson.toJson(schedule))
                writer.close()
            }
        }
        catch (_: IOError){}
    }

    suspend fun importFile(inputFile:String){
        withContext(Dispatchers.IO) {
            val stream = context.contentResolver.openInputStream(Uri.parse(inputFile))
            val writer = context.openFileOutput(filename, MODE_PRIVATE).writer()
            if (stream != null) {
                writer.write(stream.reader().readText())
            }
            writer.close()
            stream?.close()
        }
    }

    suspend fun exportFile(outDir:String){
        withContext(Dispatchers.IO) {
            val directory = Uri.withAppendedPath(Uri.parse(outDir), "")
            val stream = DocumentFile.fromTreeUri(context, directory)?.createFile("application/json", filename)
                ?.let {
                    context.contentResolver.openOutputStream(it.uri)
                }
            val writer = stream?.writer()
            writer?.write(context.openFileInput(filename).reader().readText())
            writer?.close()
            stream?.close()
        }
    }
}













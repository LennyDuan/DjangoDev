package com.example.lenny.studyresearchapp.common

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Lenny on 12/08/2017.
 */
object TypeUtil {

    // Replace \" to "
    fun removeBackSlash(text : String) : String {
        return text.replace("\\\"", "\"")
    }

    // Replace \" to " and delete "
    fun removeBackSlashAndQuote(text : String) : String {
        Log.d("TypeUtil: ", text)
        var json = text.replace("\\\"", "\"")
        Log.d("TypeUtil: ", json)
        json = json.substring(1, json.length - 1)
        Log.d("TypeUtil: ", json)
        return json
    }

    // return true if end date is or larger than today
    fun compareDate(date : String) : Boolean {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd", java.util.Locale.ENGLISH)
        val endDate = dateFormat.parse(date)
        val today = Date()
        Log.d("Today: ", today.toString())
        Log.d("End Date: ", endDate.toString())

        return today.after(endDate)
    }
}
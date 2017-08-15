package com.example.lenny.studyresearchapp.common

import android.util.Log

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
}
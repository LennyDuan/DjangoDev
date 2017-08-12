package com.example.lenny.studyresearchapp.common

/**
 * Created by Lenny on 12/08/2017.
 */
object TypeUtil {

    // Replace \" to "
    fun removeBackSlash(text : String) : String {
        return text.replace("\\\"", "\"")
    }
}
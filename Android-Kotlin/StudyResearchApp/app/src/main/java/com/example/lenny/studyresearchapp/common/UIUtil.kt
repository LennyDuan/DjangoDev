package com.example.lenny.studyresearchapp.common

import android.util.Log

/**
 * Created by Lenny on 17/08/2017.
 */
object UIUtil{

    fun validDateInput(date : String) : Boolean{
        println(date)
        val regex = Regex("2\\d{3}-\\d{1,2}-\\d{2}")
        return regex.matches(date)
    }

    fun validEmailInput(email : String) : Boolean{
        println(email)
        val regex = Regex(".+@.+\\..+")
        return regex.matches(email)
    }
}
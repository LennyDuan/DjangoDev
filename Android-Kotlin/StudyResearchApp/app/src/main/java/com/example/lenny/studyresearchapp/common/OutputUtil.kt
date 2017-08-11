package com.example.lenny.studyresearchapp.common

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.lenny.studyresearchapp.DiaryActivities

/**
 * Created by Lenny on 11/08/2017.
 */
object OutputUtil {

    fun toast(context: Context, massage: String,
                  lenght: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, massage, lenght).show()
    }

    fun nicetoast(context: Context, massage: String,
              tag: String = (DiaryActivities::javaClass).name,
              lenght: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, "[$tag]: $massage", lenght).show()
    }
}
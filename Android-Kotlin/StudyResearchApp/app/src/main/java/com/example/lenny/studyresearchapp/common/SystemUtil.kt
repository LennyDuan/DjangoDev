package com.example.lenny.studyresearchapp.common

import android.content.Context
import android.provider.Settings.Secure

/**
 * Created by Lenny on 11/08/2017.
 */
object SystemUtil {

    fun ANDROID_ID (context : Context) : String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID);
    }
}
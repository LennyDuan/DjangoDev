package com.example.lenny.studyresearchapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.reflect.KProperty

/**
 * Created by Lenny on 13/08/2017.
 */
object PrefUtil {
    class Preference(val context: Context) {

        val sharePrefs: SharedPreferences by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }
        val prefs= sharePrefs.edit()

        fun findPreference(name: String) : String{
            return sharePrefs.getString(name, name)
        }

        fun putPreference(name: String, value: String?) {
            prefs.putString(name, value).apply()
            Log.d("Pref: $name", "value = $value")
        }

        fun returnPutPreference(name: String, value: String?) : Boolean {
            if (!value.isNullOrEmpty())
                putPreference(name, value)
            else return false
            return true
        }


    }
}
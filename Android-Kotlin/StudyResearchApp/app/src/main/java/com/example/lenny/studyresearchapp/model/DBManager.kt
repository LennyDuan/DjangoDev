package com.example.lenny.studyresearchapp.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lenny.studyresearchapp.common.OutputUtil.toast


class DBManager {

    val dbName = "KtDiary"
    val dbTable = "Diary"
    val colID = "id"
    val colDate = "date"
    val colSkill = "skill"
    val colTitle = "title"
    val colEvent = "event"
    val dbVersion = 1
    val sqlCreateTable =
            "CREATE TABLE IF NOT EXISTS " + dbTable + " (" +
                    colID + "INTEGER PRIMARY KEY, " + colDate + "TEXT, " +
                    colSkill + "TEXT, " + colTitle + "TEXT, " + colEvent + "TEXT);"
    var sqlDB : SQLiteDatabase ? = null

    constructor(context: Context) {
        val db = DatabaseHelperDiary(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperDiary: SQLiteOpenHelper {

        var context: Context? = null
        constructor(context: Context): super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Log.d("SQL DB Create: ", "Create DB Successful")
            toast(context!!, "Create DB Successful")
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS " + dbTable)
        }

    }

    fun Insert(value: ContentValues): Long{
        val id = sqlDB!!.insert(dbTable, "", value)
        return id
    }
}
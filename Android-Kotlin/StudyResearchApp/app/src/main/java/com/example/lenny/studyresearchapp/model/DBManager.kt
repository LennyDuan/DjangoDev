package com.example.lenny.studyresearchapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.example.lenny.studyresearchapp.common.OutputUtil.toast


class DBManager(context: Context) {

    val dbName = "KtDiary"
    val dbTable = "Diary"
    val colID = "Id"
    val colDate = "Date"
    val colSkill = "Skill"
    val colTitle = "Title"
    val colEvent = "Event"
    val dbVersion = 1
    val sqlCreateTable =
            "CREATE TABLE IF NOT EXISTS " + dbTable + " (" +
                    colID + " INTEGER PRIMARY KEY, " + colDate + " TEXT, " +
                    colSkill + " TEXT, " + colTitle + " TEXT, " + colEvent + " TEXT);"
    var sqlDB : SQLiteDatabase ? = null
    var context: Context? = null

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

    fun Query(projection: Array<String>,selection: String, selectionArgs: Array<String>, sortOrder: String): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)
        return cursor
    }

    fun Clean() {
        context!!.deleteDatabase(dbName)
        Log.d("DB - Clean: ", "Have clean all database")
    }

    init {
        val db = DatabaseHelperDiary(context)
        sqlDB = db.writableDatabase
        this.context = context
    }
}
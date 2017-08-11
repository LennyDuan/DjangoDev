package com.example.lenny.studyresearchapp.Component

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.example.lenny.studyresearchapp.common.OutputUtil

/**
 * Created by Lenny on 11/08/2017.
 */
class DateSettings : DatePickerDialog.OnDateSetListener {
    var context : Context

    constructor(context: Context) {
        this.context = context
    }
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int){
        return OutputUtil.toast(context,"$year-$month-$day")
    }

}
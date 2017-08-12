package com.example.lenny.studyresearchapp.Component

import android.app.DatePickerDialog
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.example.lenny.studyresearchapp.R
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
        OutputUtil.toast(context,"$year-$month-$day")
    }

}
package com.example.lenny.studyresearchapp.Component

import android.app.DatePickerDialog
import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import java.util.*

/**
 * Created by Lenny on 11/08/2017.
 */
class Dialoghandler : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var calender: Calendar = Calendar.getInstance()
        var year = calender.get(Calendar.YEAR)
        var month = calender.get(Calendar.MONTH)
        var day = calender.get(Calendar.DAY_OF_MONTH)
        var dialog : DatePickerDialog
        var dateSetting: DateSettings = DateSettings(activity)
        dialog = DatePickerDialog(activity, dateSetting, year, month, day)
        return dialog
    }

}
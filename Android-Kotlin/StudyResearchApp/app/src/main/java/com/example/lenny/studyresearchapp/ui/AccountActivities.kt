package com.example.lenny.studyresearchapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import kotlinx.android.synthetic.main.activity_account_activities.*
import java.util.*

class AccountActivities : AppCompatActivity() {

    private var mDateStartSetListener : DatePickerDialog.OnDateSetListener? = null
    private var mDateEndSetListener : DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        dateStartPickerInit(account_start_date)
        dateEndPickerInit(account_end_date)
        studyFieldSelecterInit()
    }

    private fun studyFieldSelecterInit() {
        var studyField : Array<String>? = null
        studyField = arrayOf("code", "football")

        var arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, studyField)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        account_study_spinner.adapter = arrayAdapter

        account_study_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                account_study_text.setText(studyField!![position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }


    // Navigation Item Change Activities
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                toast(this,"Location: Account Page")
            }
            R.id.navigation_diary -> {
                val intent = Intent(this, DiaryActivities::class.java)
                startActivity(intent)
            }
            R.id.navigation_ques -> {
                val intent = Intent(this, QuestionnaireActivities::class.java)
                startActivity(intent)
            }
        }
        false
    }

    // Date Start picker
    private fun dateStartPickerInit(editView : TextInputEditText) {
        mDateStartSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date: String = "$year-$month-$day"
            editView.setText(date)
            hideSoftKeyboard()
        }

            editView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                    this@AccountActivities, android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateStartSetListener, year, month, day
            )
            dialog.show()
        }
    }
    // Date End picker
    private fun dateEndPickerInit(editView : TextInputEditText) {
        mDateEndSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date : String = "$year-$month-$day"
            editView.setText(date)
            hideSoftKeyboard()
        }

        editView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                    this@AccountActivities, android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateEndSetListener, year, month, day
            )
            dialog.show()
        }
    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}

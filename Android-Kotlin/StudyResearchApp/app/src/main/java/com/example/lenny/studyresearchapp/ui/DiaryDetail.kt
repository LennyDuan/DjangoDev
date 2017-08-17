package com.example.lenny.studyresearchapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.lenny.studyresearchapp.R
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import kotlinx.android.synthetic.main.activity_diary_detail.*
import java.util.*

class DiaryDetail : AppCompatActivity() {

    private var prefs : PrefUtil.Preference? = null
    private var userEmail : String? = null
    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        prefs = PrefUtil.Preference(this)
        userEmail = prefs!!.findPreference("account_final_address")

        initUI()
    }

    private fun initUI() {
        diary_date_text.setText(Date().toString())
    }
}

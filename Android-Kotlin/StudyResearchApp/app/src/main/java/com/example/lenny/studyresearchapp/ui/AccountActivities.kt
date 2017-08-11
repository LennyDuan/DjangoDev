package com.example.lenny.studyresearchapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.lenny.studyresearchapp.Component.Dialoghandler
import com.example.lenny.studyresearchapp.common.OutputUtil
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.common.SystemUtil
import kotlinx.android.synthetic.main.activity_account_activities.*

class AccountActivities : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        toast(this, SystemUtil.ANDROID_ID(this))
        account_start_date.setOnClickListener(){
            showStartDateDialig()
        }
    }

    fun showStartDateDialig() {
        val dialogHandler : Dialoghandler = Dialoghandler()
        dialogHandler.show(supportFragmentManager, "DatePicker")
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
}

package com.example.lenny.studyresearchapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import kotlinx.android.synthetic.main.activity_diary_activities.*

class AccountActivities : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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

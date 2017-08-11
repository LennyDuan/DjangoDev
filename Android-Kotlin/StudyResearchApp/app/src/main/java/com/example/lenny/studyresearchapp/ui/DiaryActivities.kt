package com.example.lenny.studyresearchapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import kotlinx.android.synthetic.main.activity_diary_activities.*

class DiaryActivities : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_activities)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    // Navigation Item Change Activities
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                val intent = Intent(this, AccountActivities::class.java)
                startActivity(intent)
            }
            R.id.navigation_diary -> {
                toast(this,"Location: Diary Page")
            }
            R.id.navigation_ques -> {
                val intent = Intent(this, QuestionnaireActivities::class.java)
                startActivity(intent)
            }
        }
        false
    }
}

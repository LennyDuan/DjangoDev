package com.example.lenny.studyresearchapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectStatus
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import kotlinx.android.synthetic.main.activity_diary_activities.*

class DiaryActivities : AppCompatActivity() {

    private var prefs : PrefUtil.Preference? = null
    private var current_status : String? = null
    private var userEmail : String? = null

    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_activities)

        // Set Navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.menu.getItem(1).isChecked = true

        // Check Preference Status
        prefs = PrefUtil.Preference(this)
        current_status = prefs!!.findPreference("status")
        userEmail = prefs!!.findPreference("account_final_address")

        if(current_status == ProjectStatus.PRE_QUESTIONNAIRE_Done.name) {
            prefs!!.putPreference("status", ProjectStatus.DIARY.name)
            current_status = ProjectStatus.DIARY.name
        }
        checkCurrentStatus()
        toast(this, "You are in $current_status Step")
    }

    // Init Menu Item
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.diary_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.diary_menu_add -> {
                val intent = Intent(this, DiaryDetail::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Navigation Item Change Activities
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                val intent = Intent(this, AccountActivities::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
                finish()
            }
            R.id.navigation_diary -> {
                toast(this,"Location: Diary Page")
            }
            R.id.navigation_ques -> {
                val intent = Intent(this, QuestionnaireActivities::class.java)
                startActivity(intent)
                finish()
            }
        }
        false
    }

    // Set Navigation item
    private fun checkCurrentStatus() {
        if(current_status == ProjectStatus.DIARY.name) {
            setNavAbilities(true, true, false)
        } else if (current_status == ProjectStatus.DIARY_DONE.name) {
            setNavAbilities(true, false, true)
        } else {
            setNavAbilities(true, false, true)
        }
    }

    private fun setNavAbilities(
            account: Boolean, diary: Boolean, questionnaire: Boolean) {
        navigation.menu.getItem(0).isEnabled = account
        navigation.menu.getItem(1).isEnabled = diary
        navigation.menu.getItem(2).isEnabled = questionnaire
    }

}

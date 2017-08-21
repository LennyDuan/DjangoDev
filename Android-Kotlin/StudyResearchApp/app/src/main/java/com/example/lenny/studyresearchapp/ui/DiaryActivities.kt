package com.example.lenny.studyresearchapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import com.example.lenny.studyresearchapp.Component.DiaryRecycelAdapter
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectStatus
import com.example.lenny.studyresearchapp.model.DBManager
import com.example.lenny.studyresearchapp.model.Diary
import kotlinx.android.synthetic.main.activity_diary_activities.*
import java.util.ArrayList

class DiaryActivities : AppCompatActivity() {

    private var prefs : PrefUtil.Preference? = null
    private var current_status : String? = null
    private var userEmail : String? = null
    private var account_final_enddate : String? = null

    var diaryList = ArrayList<Diary>()

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
        account_final_enddate =  prefs!!.findPreference("account_final_enddate")

        if(current_status == ProjectStatus.PRE_QUESTIONNAIRE_Done.name) {
            prefs!!.putPreference("status", ProjectStatus.DIARY.name)
            current_status = ProjectStatus.DIARY.name
        }

        if(TypeUtil.compareDate(account_final_enddate.toString())) {
            prefs!!.putPreference("status", ProjectStatus.DIARY_DONE.name)
            current_status = ProjectStatus.DIARY_DONE.name
        }
        checkCurrentStatus()
        toast(this, "You are in $current_status Step")

        loadDiaryList("%")
    }

    private fun initDiaryRecycleView() {
        diary_list_recycle_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = DiaryRecycelAdapter(diaryList)
        diary_list_recycle_view.adapter = adapter
    }

    private fun loadDiaryList(text: String) {
        val projection = arrayOf("Id", "Date", "Skill", "Title", "Event")
        Log.d("DB: ", "Project: $projection")
        val dbManager = DBManager(this)
        val selectionArgs = arrayOf(text, text)
        Log.d("DB: ", "selectionArgs: $selectionArgs")
        val cursor = dbManager.Query(projection, "Title LIKE ? OR Date LIKE ? ", selectionArgs, "Date")

        diaryList.clear()
        Log.d("DB: ", "cursor is good")
        if (cursor.moveToLast()) {
            do {
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                val skill = cursor.getString(cursor.getColumnIndex("Skill"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val event = cursor.getString(cursor.getColumnIndex("Event"))
                diaryList.add(Diary(date, skill, title, event))

            } while (cursor.moveToPrevious())
        }

        initDiaryRecycleView()
    }


    // Init Menu Item
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.diary_menu, menu)
        Log.d("Menu: ", menu!!.getItem(1).toString())
        Log.d("Menu: ", menu.findItem(R.id.app_bar_search).toString())
        val sv : SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                loadDiaryList("%$query%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.diary_menu_add -> {
                if (current_status != ProjectStatus.DIARY_DONE.name) {
                    val intent = Intent(this, DiaryDetail::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    toast(this,
                            "Diary mode is over, please complete the same questionnaire again")
                }

            }
            R.id.app_bar_search -> {

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
            setNavAbilities(true, true, true)
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

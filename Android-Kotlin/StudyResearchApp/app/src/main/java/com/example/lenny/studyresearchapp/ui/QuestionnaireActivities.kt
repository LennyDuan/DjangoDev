package com.example.lenny.studyresearchapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectAPI
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import kotlinx.android.synthetic.main.activity_questionnare_activities.*

class QuestionnaireActivities : AppCompatActivity() {
    private var prefs : PrefUtil.Preference? = null
    private var current_status : String? = null
    private var accunt_study_field : String? = null

    val service = ServiceVolley()
    val apiController = APIController(service)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnare_activities)

        // Set navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Check Preference Status
        prefs = PrefUtil.Preference(this)
        current_status = prefs!!.findPreference("status")

        // Get Questionnaire
        accunt_study_field = prefs!!.findPreference("account_final_studyfield")
        Log.d("Ques Page: ", accunt_study_field)

        // Get all Questions
        getQuestionViaStudyField()

    }

    private fun getQuestionViaStudyField() {
        val url : String = ProjectAPI.GET_Question_List_URL.url + accunt_study_field!! + "/field"
        Log.d("Questions GET URL: ", url)
        apiController.get(url) { response ->
            Log.d("Question Req: " , response)
            Log.d("Question Req: " , TypeUtil.removeBackSlashAndQuote(response!!))
        }
    }

    // Navigation Item Change Activities
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_account -> {
                val intent = Intent(this, AccountActivities::class.java)
                startActivity(intent)
            }
            R.id.navigation_diary -> {
                val intent = Intent(this, DiaryActivities::class.java)
                startActivity(intent)
            }
            R.id.navigation_ques -> {
                toast(this,"Location: Questionnaire Page")
            }
        }
        false
    }
}

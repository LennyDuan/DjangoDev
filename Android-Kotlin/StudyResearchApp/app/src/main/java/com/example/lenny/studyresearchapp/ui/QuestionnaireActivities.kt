package com.example.lenny.studyresearchapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.example.lenny.studyresearchapp.Component.AnswerListAdapter
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectAPI
import com.example.lenny.studyresearchapp.data.ProjectStatus
import com.example.lenny.studyresearchapp.model.Answer
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import kotlinx.android.synthetic.main.activity_questionnare_activities.*
import kotlinx.android.synthetic.main.question_dialog.view.*
import org.json.JSONArray
import org.json.JSONObject
import android.os.Handler


class QuestionnaireActivities : AppCompatActivity() {
    private var prefs : PrefUtil.Preference? = null
    private var current_status : String? = null
    private var accunt_study_field : String? = null
    private var anwserList = ArrayList<Answer>()
    private var feedbackID : String? = null
    private var progressDialog: ProgressDialog? = null

    var answerListAdapter: AnswerListAdapter? = null
    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnare_activities)

        // Set navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        progressDialog = ProgressDialog(this)

        // Check Preference Status
        prefs = PrefUtil.Preference(this)
        current_status = prefs!!.findPreference("status")
        feedbackID = prefs!!.findPreference("account_final_id")

        // Get Questionnaire
        accunt_study_field = prefs!!.findPreference("account_final_studyfield")
        Log.d("Ques Page: ", accunt_study_field)
        Log.d("Current Status: ", current_status)

        // Init First Questionnaire
        if(current_status == ProjectStatus.ACCOUNT_DONE.name) {
            // Get all Questions
            getQuestionViaStudyField()
            // Create List View
            createAnswerListView()
            current_status = ProjectStatus.PRE_QUESTIONNAIRE.name
            confirmBtn.isEnabled = true
            confirmBtn.setOnClickListener(submitBtnClickListener)
        }
    }

    private fun createAnswerListView() {
        answerListAdapter = AnswerListAdapter(this, anwserList, LayoutInflater.from(this))
        answers_listView.adapter = answerListAdapter
        answers_listView.onItemClickListener = listItemClickListener
    }

    private val listItemClickListener = AdapterView.OnItemClickListener {
        _: AdapterView<*>, _: View, position: Int, _: Long ->
        showAnswerDialog(position)
    }

    // show answer dialog
    private fun showAnswerDialog(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.question_dialog, null)
        AlertDialog.Builder(this)
                .setTitle("Question Dialog:")
                .setMessage((position + 1).toString() + ". " + anwserList[position].answer_question)
                .setView(dialogView)
                .setIcon(R.drawable.ic_question_answer_black_24dp)
                .setPositiveButton(android.R.string.ok) {
                    _, _ ->
                    anwserList[position].answer_answer = dialogView.edit_answer.text.toString()
                    answerListAdapter!!.notifyDataSetChanged()
                }
                .setNegativeButton(android.R.string.cancel) {
                    _, _ ->
                }
                .show()
    }

    // Submit btn function
    private val submitBtnClickListener = View.OnClickListener {
        if(!allAnswer()) {
            toast(this, "Please answer all questions!")
        }
        else {
            uploadQuestionnaire()
            toast(this, "Finish Questionnaires, start writing diary now!")
            confirmBtn.isEnabled = false;
        }
    }

    private fun uploadQuestionnaire() {
        progressDialog!!.setMessage("Uploading answers ...")
        progressDialog!!.setIcon(R.drawable.ic_sync_black_24dp)
        progressDialog!!.show()
        val url = ProjectAPI.POST_ANSWERS_TO_FEEDBACK.url + feedbackID + "/feedback/"
        Log.d("Answer POST: ", url)
        (0..anwserList.size - 1).forEach { item ->
            val params = JSONObject()
            params.put("answer_id", anwserList[item].answer_id)
            params.put("answer_question", anwserList[item].answer_question)
            if(current_status == ProjectStatus.PRE_QUESTIONNAIRE.name) {
                params.put("answer_before", anwserList[item].answer_answer)
                params.put("answer_after", "")
            }
            apiController.post(url, params) {}
        }

        val progressRunnable = Runnable {
            progressDialog!!.cancel()
            prefs!!.putPreference("status", ProjectStatus.PRE_QUESTIONNAIRE_Done.name)
        }
        val pdCanceller = Handler()
        pdCanceller.postDelayed(progressRunnable, 1000)
    }

    private fun allAnswer(): Boolean {
        (0..anwserList.size - 1).forEach { item ->
            if (anwserList[item].answer_answer == null)
                return false
        }
        return true
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


    // Init Answer List in Questionnaire Page
    private fun getQuestionViaStudyField() {
        val url : String = ProjectAPI.GET_Question_List_URL.url + accunt_study_field!! + "/field"
        Log.d("Questions GET URL: ", url)
        apiController.get(url) { response ->
            Log.d("Question Req: " , response)
            Log.d("Question Req: " , TypeUtil.removeBackSlashAndQuote(response!!))
            initAnswerList(TypeUtil.removeBackSlashAndQuote(response))
        }
    }

    private fun  initAnswerList(jsonString: String) {
        val jsonArray = JSONArray(jsonString)

        (0..jsonArray.length() - 1).forEach { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            if (null != jsonObject) {
                if (jsonObject.has("fields")) {
                    val fields = jsonObject.getJSONObject("fields")

                    val answer_id = if (fields.has("question_id"))
                        feedbackID + "_" + fields.getString("question_id") else ""
                    val answer_question = if (fields.has("question_text"))
                        fields.getString("question_text") else ""
                    val answer = Answer(answer_id, answer_question, null)
                    anwserList.add(answer)
                }
            }
        }
        Log.d("AnswerList: ", anwserList.size.toString())
        Log.d("AnswerList: ", anwserList.toString())
    }


}

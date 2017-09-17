package com.example.lenny.studyresearchapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import kotlinx.android.synthetic.main.activity_account_activities.*
import android.support.v7.app.AlertDialog
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lenny.studyresearchapp.common.SystemUtil
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil.Preference
import com.example.lenny.studyresearchapp.data.ProjectAPI
import com.example.lenny.studyresearchapp.data.ProjectStatus
import org.json.JSONArray
import org.json.JSONException

@Suppress("DEPRECATION")
class AccountActivities : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var prefs : Preference? = null
    private var current_status : String? = null
    private var android_id :  String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)
        // Prevent App from Exception
        Thread.setDefaultUncaughtExceptionHandler {
            _, paramThrowable -> Log.e("Error" + Thread.currentThread().stackTrace[2], paramThrowable.localizedMessage)
        }

        // Set navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.menu.getItem(0).isChecked = true

        progressDialog = ProgressDialog(this)

        // Check Preference Status
        prefs = Preference(this)

        current_status = prefs!!.findPreference("status")
        if (current_status == "status") {
            current_status = ProjectStatus.INIT.name
            prefs!!.putPreference("status", current_status)
        }
        Log.d("Current Status: ", current_status)

        checkCurrentStatus()

        // Init UI components
        initNetwork()
        val progressRunnable = Runnable {
            initUniqueIDTextInputField()
        }
        val pdCanceller = Handler()
        pdCanceller.postDelayed(progressRunnable, 200)

        // Init Set Button
        hideSoftKeyboard()
        //toast(this, "You are in $current_status Step")
        button_go.setOnClickListener {
            var intent = Intent(this, BasicInfo::class.java)
            if(current_status == ProjectStatus.INIT.name) {
                intent = Intent(this, BasicInfo::class.java)
            }
            else if(current_status == ProjectStatus.ACCOUNT_DONE.name ||
                    current_status == ProjectStatus.PRE_QUESTIONNAIRE.name) {
                intent = Intent(this, QuestionnaireActivities::class.java)
            }
            else if(current_status == ProjectStatus.PRE_QUESTIONNAIRE_Done.name ||
                    current_status == ProjectStatus.DIARY.name) {
                intent = Intent(this, DiaryActivities::class.java)
            }
            else if(current_status == ProjectStatus.DIARY_DONE.name ||
                    current_status == ProjectStatus.AFTER_QUESTIONNAIRE.name) {
                intent = Intent(this, QuestionnaireActivities::class.java)
            }


            if(current_status == ProjectStatus.AFTER_QUESTIONNAIRE_DONE.name) {
                toast(this, "Thanks for your help!")
            } else {
                startActivity(intent)
                finish()
            }
        }
    }

    private fun checkCurrentStatus() {
        current_status = prefs!!.findPreference("status")

        if(current_status!! == ProjectStatus.DIARY.name) {
            setNavAbilities(true, true, false)
        }  else if (current_status!! == ProjectStatus.PRE_QUESTIONNAIRE_Done.name) {
            setNavAbilities(true, true, false)
        }   else if (current_status!! == ProjectStatus.PRE_QUESTIONNAIRE.name) {
            setNavAbilities(true, false, true)
        } else if (current_status!! == ProjectStatus.AFTER_QUESTIONNAIRE.name) {
            setNavAbilities(true, true, true)
            //toast(this, "You have completed diary and study period is expired." +
              //      " Please complete the questionnaire again.")
        } else if (current_status!! == ProjectStatus.ACCOUNT_DONE.name) {
            setNavAbilities(true, false, true)
        } else if (current_status!! == ProjectStatus.INIT.name) {
            setNavAbilities(true, false, false)
        } else if (current_status!! == ProjectStatus.NONE.name) {
            setNavAbilities(true, false, false)
            //toast(this, "Unable to connect server, we will come back soon")
        } else if (current_status!! == ProjectStatus.DIARY_DONE.name) {
            setNavAbilities(true, true, true)
            //toast(this, "You have completed diary and study period is expired." +
             //       " Please complete the questionnaire again.")
        } else if (current_status!! == ProjectStatus.AFTER_QUESTIONNAIRE_DONE.name) {
            setNavAbilities(true, true, true)
            //toast(this, "You have completed this study, All your data has been sent to our study team," +
              //      "Thanks very much!")
        } else {
            setNavAbilities(true, false, false)
            prefs!!.putPreference("status", ProjectStatus.INIT.name)
        }
        Log.d("Current ID: ", prefs!!.findPreference("account_final_id"))
        Log.d("Current status: ", "You are in $current_status Mode")
    }

    private fun setNavAbilities(
            account: Boolean, diary: Boolean, questionnaire: Boolean) {
        navigation.menu.getItem(0).isEnabled = account
        navigation.menu.getItem(1).isEnabled = diary
        navigation.menu.getItem(2).isEnabled = questionnaire
    }

    // RESTful GET studies
    private fun startStudyDownload(url: String) {
        makeRequestWithVolley(url)
    }

    private fun downloadStudyComplete(result: String?) {
        var jsonArr = TypeUtil.removeBackSlash(result!!)
        jsonArr = jsonArr.substring(1, jsonArr.length - 1)
        Log.d("JSonC", jsonArr)

        val jsonArray = JSONArray(jsonArr)

        (0..jsonArray.length() - 1).forEach { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            if (null != jsonObject) {
                if (jsonObject.has("fields")) {
                    val fields = jsonObject.getJSONObject("fields")

                    val study_field = if (fields.has("study_field"))
                        fields.getString("study_field") else ""
                    val end_date = if (fields.has("study_end_date"))
                    fields.getString("study_end_date") else ""
                    val start_date = if (fields.has("study_start_date"))
                        fields.getString("study_start_date") else ""
                    android_id = SystemUtil.ANDROID_ID(this)
                    Log.d("Data Study: ", fields.toString())
                    prefs!!.putPreference("account_final_studyfield", study_field)
                    prefs!!.putPreference("account_final_id", "$study_field-$android_id-$start_date")
                    prefs!!.putPreference("account_final_startdate", start_date)
                    prefs!!.putPreference("account_final_enddate", end_date)
                    current_status = ProjectStatus.INIT.name
                    prefs!!.putPreference("status", current_status)
                    progressDialog?.dismiss()
                }
            }
        }
    }

    private fun makeRequestWithVolley(url: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    try {
                        downloadStudyComplete(response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { })
        queue.add(stringRequest)
        queue.start()
    }

    // Check network connection
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun  needStudyAPI(): Boolean {
        Log.d("current status: ", current_status)
        if (current_status!! == ProjectStatus.INIT.name) {
            return true
        }
        return false
    }

    private fun initNetwork() {
        if (isNetworkConnected()) {
            progressDialog?.setMessage("Please wait or restart app...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()

            if (needStudyAPI()){
                Log.d("URL: ", ProjectAPI.GET_ACTIVE_STUDY_URL.url)
                startStudyDownload(ProjectAPI.GET_ACTIVE_STUDY_URL.url)
            } else {
                progressDialog?.dismiss()
            }

        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { dialog, which -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
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
                finish()
            }
            R.id.navigation_ques -> {
                val intent = Intent(this, QuestionnaireActivities::class.java)
                startActivity(intent)
                finish()
            }
        }
        false
    }

    @SuppressLint("SetTextI18n")
    private fun initUniqueIDTextInputField() {
        account_id.setText(prefs!!.findPreference("account_final_id"))
        textView_status.text = "Guide: " + current_status
        textView_status_detail.text = ProjectStatus.valueOf(current_status!!).guide + "\n" +
                "Notice! End date:  " + prefs!!.findPreference("account_final_enddate")
    }


    // Hide soft keyboard
    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

}

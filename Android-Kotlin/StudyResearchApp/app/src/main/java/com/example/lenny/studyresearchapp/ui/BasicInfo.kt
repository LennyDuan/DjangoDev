package com.example.lenny.studyresearchapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectAPI
import com.example.lenny.studyresearchapp.model.FeedbackStatus
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_basic_info.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.lenny.studyresearchapp.data.ProjectStatus


/**
 * Created by Lenny on 15/09/2017.
 */
class BasicInfo : AppCompatActivity()  {

    private var progressDialog: ProgressDialog? = null
    private var prefs : PrefUtil.Preference? = null
    private var userAge = "10"
    private var userGender = "Male"
    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_info)
        seekbar_age.max = 100
        seekbar_age.setOnSeekBarChangeListener(mOnSeekBarChangedListener)
        confirmBtn.setOnClickListener {
            uploadUserInfoToWebServer()
        }

        boy_btn.setOnClickListener {
            girl_btn.isChecked = false
            userGender = "Boy"
        }

        girl_btn.setOnClickListener {
            boy_btn.isChecked = false
            userGender = "Girl"
        }
    }

    private val mOnSeekBarChangedListener = object : OnSeekBarChangeListener {
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        @SuppressLint("SetTextI18n")
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            textViewAge.text = "Select Your Age: " + progress.toString()
            userAge = progress.toString()
        }
    }

    override fun onBackPressed() {
        backToGuide()
    }

    private fun backToGuide() {
        val intent = Intent(this, AccountActivities::class.java)
        startActivity(intent)
        finish()
    }


    // Upload data to server
    private fun uploadUserInfoToWebServer() {
        progressDialog?.setMessage("Creating User Account Data ... ")
        progressDialog?.setCancelable(true)
        progressDialog?.show()
        postUserFeedback()
    }

    // Post to userinfo data in server
    private fun postUserInfo() {
        val userInfo_id : String = prefs!!.findPreference("account_final_id")
        val userInfo_start_date : String = prefs!!.findPreference("account_final_startdate")
        val userInfo_end_date : String = prefs!!.findPreference("account_final_enddate")
        Log.d("Pref: -- ", prefs!!.findPreference("account_final_studyfield"))
        val studyField : String = prefs!!.findPreference("account_final_studyfield")
        val url: String = ProjectAPI.POST_USER_INFO.url + "$studyField/studyField/$userInfo_id/feedbackID/"
        Log.d("POST UserInfo: ","Post url: $url")
        val params = JSONObject()
        params.put("userInfo_id", userInfo_id)
        params.put("userInfo_age", userAge)
        params.put("userInfo_gender", userGender)
        params.put("userInfo_start_date", userInfo_start_date)
        params.put("userInfo_end_date", userInfo_end_date)
        apiController.post(url, params) { _ ->
            progressDialog?.dismiss()
            prefs!!.putPreference("status", ProjectStatus.ACCOUNT_DONE.name)
            backToGuide()
        }
    }

    // Post to feedback data in server
    private fun postUserFeedback() {
        val feedback_id : String= prefs!!.findPreference("account_final_id")
        val feedback_state : String= FeedbackStatus.INI.name
        val url: String = ProjectAPI.POST_FEEDBACK_URL.url
        Log.d("POST: ","Post url: $url")
        Log.d("POST: ","Post Body: $feedback_id - $feedback_state")
        val params = JSONObject()
        params.put("feedback_id", feedback_id)
        params.put("feedback_state", feedback_state)
        apiController.post(url, params) { _ ->
            postUserInfo()
        }
    }
}
package com.example.lenny.studyresearchapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import kotlinx.android.synthetic.main.activity_account_activities.*
import java.util.*
import android.support.v7.app.AlertDialog
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lenny.studyresearchapp.common.SystemUtil
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil.Preference
import com.example.lenny.studyresearchapp.data.ProjectStatus
import org.json.JSONArray
import org.json.JSONException
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class AccountActivities : AppCompatActivity() {

    private var mDateStartSetListener : DatePickerDialog.OnDateSetListener? = null
    private var mDateEndSetListener : DatePickerDialog.OnDateSetListener? = null
    internal var progressDialog: ProgressDialog? = null
    internal val GET_STUDY_URL: String = "http://10.0.2.2:8000/polls/api/v1/study/"
    private var account_final_id : String? = null
    private var account_final_address : String? = null
    private var account_final_username : String? = null
    private var account_final_startdate : String? = null
    private var account_final_enddate : String? = null
    private var account_final_studyfield : String? = null
    private var prefs : Preference? = null
    private var current_status : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)

        // Set navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Check Preference Status
        prefs = Preference(this)
        current_status = prefs!!.findPreference("status")
        checkCurrentStatus()
        // Init UI components
        dateStartPickerInit(account_start_date)
        dateEndPickerInit(account_end_date)
        initNetwork()
        initUniqueIDTextInputField()

        // Init Set Button
        account_btn_confirm.setOnClickListener {
            saveDataToPreference()
        }
        account_btn_reset.setOnClickListener {
            resetDataFromPreference()
        }
    }


    private fun resetDataFromPreference() {
        AlertDialog.Builder(this).setTitle("Notice!!")
                .setMessage("Click 'ok' and reset account will lose all data")
                .setPositiveButton(android.R.string.ok) {
                    dialog, which ->
                    prefs!!.putPreference("status", ProjectStatus.INIT.name)
                    Log.d("Status: ", prefs?.findPreference("status"))
                    checkCurrentStatus()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
                .setIcon(android.R.drawable.alert_dark_frame)
    }

    private fun saveDataToPreference() {
        account_final_id = account_id.text?.toString()
        account_final_address = account_address.text?.toString()
        account_final_username = account_username.text?.toString()
        account_final_startdate = account_start_date.text?.toString()
        account_final_enddate = account_end_date.text?.toString()
        account_final_studyfield = account_study_text.text?.toString()

        if (savedAccountDataPref()) {
            prefs!!.putPreference("status", ProjectStatus.ACCOUNT_DONE.name)
        } else {
            toast(this, "Please fill in all input areas!")
        }
        checkCurrentStatus()
    }

    private fun  savedAccountDataPref(): Boolean {
        return prefs!!.returnPutPreference("account_final_id", account_final_id)
                && prefs!!.returnPutPreference("account_final_address", account_final_address)
                && prefs!!.returnPutPreference("account_final_username", account_final_username)
                && prefs!!.returnPutPreference("account_final_startdate", account_final_startdate)
                && prefs!!.returnPutPreference("account_final_enddate", account_final_enddate)
                && prefs!!.returnPutPreference("account_final_studyfield", account_final_studyfield)
    }

    private fun checkCurrentStatus() {
        current_status = prefs!!.findPreference("status")
        if(current_status!! == ProjectStatus.DIARY.name) {
            setUIAbilities(false, false, false, false, false, false, true)
            setNavAbilities(true, true, false)
        } else if (current_status!! == ProjectStatus.PRE_QUESTIONNAIRE.name) {
            setUIAbilities(false, false, false, false, false, false, true)
            setNavAbilities(true, false, false)
        } else if (current_status!! == ProjectStatus.AFTER_QUESTIONNAIRE.name) {
            setUIAbilities(false, false, false, false, false, false, true)
            setNavAbilities(false, false, false)
        } else if (current_status!! == ProjectStatus.ACCOUNT_DONE.name) {
            setUIAbilities(false, false, false, false, false, false, true)
            setNavAbilities(true, false, true)
        } else if (current_status!! == ProjectStatus.INIT.name) {
            setUIAbilities(true, true, true, true, true, true, false)
            setNavAbilities(true, false, false)
        } else if (current_status!! == ProjectStatus.NONE.name) {
            setUIAbilities(false, false, false, false, false, false, false)
            setNavAbilities(true, false, false)
            toast(this, "Unable to connect server, we will come back soon")
        } else {
            setUIAbilities(true, true, true, true, true, true, false)
            setNavAbilities(true, false, false)
            prefs!!.putPreference("status", ProjectStatus.INIT.name)
        }
        toast(this, "You are in $current_status Mode")
        setUITextFromPref()
    }

    @SuppressLint("SetTextI18n")
    private fun setUITextFromPref() {
        current_status =  prefs?.findPreference("status")
        account_id.setText(prefs?.findPreference("account_final_id"))
        account_address.setText(prefs?.findPreference("account_final_address"))
        account_username.setText(prefs?.findPreference("account_final_username"))
        account_start_date.setText(prefs?.findPreference("account_final_startdate"))
        account_end_date.setText(prefs?.findPreference("account_final_enddate"))
        account_study_text.setText(prefs?.findPreference("account_final_studyfield"))
    }


    private fun setUIAbilities(
            emailAddress: Boolean, username: Boolean, startDate: Boolean,
            endDate:Boolean, studyspinner: Boolean, confirmBtn: Boolean, resetBtn: Boolean) {
        account_address.isEnabled = emailAddress
        account_username.isEnabled = username
        account_start_date.isEnabled = startDate
        account_end_date.isEnabled = endDate
        account_study_spinner.isEnabled = studyspinner
        account_btn_confirm.isEnabled = confirmBtn
        account_btn_reset.isEnabled = resetBtn
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
        val studyField  = ArrayList<String>()
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
                    Log.d("JSonC", study_field.toString())
                    studyField.add(study_field)
                }
            }
        }
        if (studyField.isEmpty()) {
            prefs!!.putPreference("status", ProjectStatus.NONE.name)
            checkCurrentStatus()
        }
        if (progressDialog != null) {
            progressDialog?.dismiss()
            studyFieldSelectorInit(studyField)
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

    private fun studyFieldSelectorInit(studyField: ArrayList<String>) {
        val array = arrayOfNulls<String>(studyField.size)
        val studyFiledArray = studyField.toArray(array)

        val arrayAdapter : ArrayAdapter<String?> = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, studyFiledArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        account_study_spinner.adapter = arrayAdapter
        account_study_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                account_study_text.setText(studyFiledArray.get(position))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    // Check network connection
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun initNetwork() {
        if (isNetworkConnected()) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Please wait...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()

            startStudyDownload(GET_STUDY_URL)
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
            }
            R.id.navigation_ques -> {
                val intent = Intent(this, QuestionnaireActivities::class.java)
                startActivity(intent)
            }
        }
        false
    }

    private fun initUniqueIDTextInputField() {
        account_id.setText(SystemUtil.ANDROID_ID(this))
    }

    // Date Start picker
    private fun dateStartPickerInit(editView : TextInputEditText) {
        mDateStartSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date: String = "$year-$month-$day"
            editView.setText(date)
            hideSoftKeyboard()
        }

        editView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                    this@AccountActivities, android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateStartSetListener, year, month, day
            )
            dialog.show()
        }
    }
    // Date End picker
    private fun dateEndPickerInit(editView : TextInputEditText) {
        mDateEndSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date : String = "$year-$month-$day"
            editView.setText(date)
            hideSoftKeyboard()
        }

        editView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                    this@AccountActivities, android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateEndSetListener, year, month, day
            )
            dialog.show()
        }
    }

    // Hide soft keyboard
    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

}

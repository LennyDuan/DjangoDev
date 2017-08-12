package com.example.lenny.studyresearchapp

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.example.lenny.studyresearchapp.common.SystemUtil
import com.example.lenny.studyresearchapp.common.TypeUtil
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

    val sharePrefs: SharedPreferences by lazy { this.getSharedPreferences("default", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_activities)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        dateStartPickerInit(account_start_date)
        dateEndPickerInit(account_end_date)
        initNetwork()
        initTextInputField()
        account_btn_confirm.setOnClickListener {
            saveDataToPreference()
        }
        account_btn_reset.setOnClickListener {
            resetDataFromPreference()
        }
    }

    private fun initTextInputField() {
        account_id.setText(SystemUtil.ANDROID_ID(this))
    }

    private fun saveDataToPreference() {
        account_final_id = account_id.text?.toString()
        account_final_address = account_address.text?.toString()
        account_final_username = account_username.text?.toString()
        account_final_startdate = account_start_date.text?.toString()
        account_final_enddate = account_end_date.text?.toString()
        account_final_studyfield = account_study_text.text?.toString()

        Log.d("UserInfo:", account_final_id)
        Log.d("UserInfo:", account_final_address)
        Log.d("UserInfo:", account_final_username )
        Log.d("UserInfo:", account_final_startdate)
        Log.d("UserInfo:", account_final_enddate )
        Log.d("UserInfo:", account_final_studyfield )

        Log.d("Prefs: ", sharePrefs.getString("account_final_id", "account_final_id"))
        Log.d("Prefs: ", sharePrefs.getString("account_final_address", "account_final_address"))
        Log.d("Prefs: ", sharePrefs.getString("account_final_username", "account_final_username"))
        Log.d("Prefs: ", sharePrefs.getString("account_final_startdate", "account_final_startdate"))
        Log.d("Prefs: ", sharePrefs.getString("account_final_enddate", "account_final_enddate"))
        Log.d("Prefs: ", sharePrefs.getString("account_final_studyfield", "account_final_studyfield"))

        if (savedDate()) {
            account_btn_confirm.isEnabled = false
            account_btn_reset.isEnabled = true
        } else {
            toast(this, "Please fill in all input areas!")
        }
    }

    private fun  savedDate(): Boolean {
        val prefs = sharePrefs.edit()
        if (!account_final_id.isNullOrEmpty())
            prefs.putString("account_final_id" ,account_final_id);
        else return false

        if (!account_final_address.isNullOrEmpty())
            prefs.putString("account_final_address", account_final_address);
        else return false

        if (!account_final_username.isNullOrEmpty())
            prefs.putString("account_final_username", account_final_username);
        else return false

        if (!account_final_startdate.isNullOrEmpty())
            prefs.putString("account_final_startdate", account_final_startdate);
        else return false

        if (!account_final_enddate.isNullOrEmpty())
            prefs.putString("account_final_enddate", account_final_enddate);
        else return false

        if (!account_final_studyfield.isNullOrEmpty())
            prefs.putString("account_final_studyfield", account_final_studyfield);
        else return false

        prefs.apply()
        return true
    }

    private fun resetDataFromPreference() {
        account_btn_reset.isEnabled = false
        account_btn_confirm.isEnabled = true
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

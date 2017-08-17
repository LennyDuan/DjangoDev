package com.example.lenny.studyresearchapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.example.lenny.studyresearchapp.common.OutputUtil
import com.example.lenny.studyresearchapp.common.TypeUtil
import com.example.lenny.studyresearchapp.data.PrefUtil
import com.example.lenny.studyresearchapp.data.ProjectAPI
import com.example.lenny.studyresearchapp.common.OutputUtil.toast
import com.example.lenny.studyresearchapp.model.Diary
import com.example.lenny.studyresearchapp.network.APIController
import com.example.lenny.studyresearchapp.network.ServiceVolley
import kotlinx.android.synthetic.main.activity_diary_detail.*
import org.json.JSONArray
import java.util.*

class DiaryDetail : AppCompatActivity() {

    private var prefs : PrefUtil.Preference? = null
    private var userEmail : String? = null
    private var diary: Diary? = null
    val service = ServiceVolley()
    val apiController = APIController(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        prefs = PrefUtil.Preference(this)
        userEmail = prefs!!.findPreference("account_final_address")

        initUI()
    }

    override fun onBackPressed() {
        backToDiaryList()
    }


    private fun saveDataToDB() {
        toast(this, "Create diary successful")
    }

    private fun initUI() {
        diary_date_text.setText(Date().toString())
        diary_submit_btn.setOnClickListener(submitBtnClickListener)
        downLoadSkills()
    }

    // Init Skills Spinners
    private fun downLoadSkills() {
        val url : String = ProjectAPI.GET_SKILLS_LIST_URL.url
        Log.d("Skill GET URL: ", url)
        apiController.get(url) { response ->
            Log.d("Question Req: " , response)
            initList(TypeUtil.removeBackSlashAndQuote(response!!))
        }
    }

    private fun  initList(skillString: String) {
        val jsonArray = JSONArray(skillString)
        val skillList = ArrayList<String>()
        skillList.add("N/A")
        (0..jsonArray.length() - 1).forEach { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            if (null != jsonObject) {
                if (jsonObject.has("fields")) {
                    val fields = jsonObject.getJSONObject("fields")

                    val skill_name = if (fields.has("skill_name"))
                        fields.getString("skill_name")else ""
                    skillList.add(skill_name)
                }
            }
        }
        Log.d("Skill List: ", skillList.toString())
        initSkillSpinner(skillList)
    }

    private fun initSkillSpinner(skills: ArrayList<String>) {
        val array = arrayOfNulls<String>(skills.size)
        val skillsArray = skills.toArray(array)

        val arrayAdapter : ArrayAdapter<String?> = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, skillsArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diary_spinner_skill.adapter = arrayAdapter
        diary_spinner_skill.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position ==  0) {
                    toast(this@DiaryDetail, "Please select skill might be improved today")
                } else {
                    diary_text_skill.setText(skillsArray.get(position))
                    Log.d("Skill Selected: ", skillsArray.get(position).toString())
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    // Submit btn function
    private val submitBtnClickListener = View.OnClickListener{
        if(finishDiary()) {
            OutputUtil.toast(this, "Please fill in the blanks")
        }
        else {
            AlertDialog.Builder(this)
                    .setTitle("Create Diary?")
                    .setMessage("Notice: You can't modify your diary in the future")
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setPositiveButton(android.R.string.yes) {
                        _, _ ->
                        initDiary()
                        saveDataToDB()
                        backToDiaryList()
                    }
                    .setNegativeButton(android.R.string.cancel) {
                        _, _ ->
                    }
                    .show()
        }
    }

    private fun initDiary() {
        diary = Diary(diary_date_text.text.toString(), diary_text_skill.text.toString(),
                diary_title.text.toString(), diary_event.text.toString())
    }

    private fun backToDiaryList() {
        val intent = Intent(this, DiaryActivities::class.java)
        startActivity(intent)
        finish()
    }
    private fun finishDiary(): Boolean {
        return diary_text_skill.text.isNullOrEmpty()
                || diary_event.text.isNullOrEmpty()
                || diary_title.text.isNullOrEmpty()
    }

}

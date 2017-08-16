package com.example.lenny.studyresearchapp.Component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.lenny.studyresearchapp.QuestionnaireActivities
import com.example.lenny.studyresearchapp.R
import com.example.lenny.studyresearchapp.model.Answer
import kotlinx.android.synthetic.main.answer_list.view.*

/**
 * Created by Lenny on 16/08/2017.
 */
class AnswerListAdapter(context: Context, answerList: ArrayList<Answer>, layoutInflater: LayoutInflater) : BaseAdapter() {

    var context: Context
    var answerList: ArrayList<Answer>
    var layoutInflater: LayoutInflater

    init {
        this.context = context
        this.answerList = answerList
        this.layoutInflater = layoutInflater
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var cview: View
        cview = layoutInflater.inflate(R.layout.answer_list, null)
        cview.answer_id.text = answerList[position].answer_id
        cview.answer_question.text = answerList[position].answer_question
        cview.answer_answer.text = answerList[position].answer_answer
        return cview
    }

    override fun getItem(position: Int): Any {
        return answerList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return answerList.size
    }
}

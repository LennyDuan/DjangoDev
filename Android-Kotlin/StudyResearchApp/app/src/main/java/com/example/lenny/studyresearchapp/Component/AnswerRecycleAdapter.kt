package com.example.lenny.studyresearchapp.Component

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.lenny.studyresearchapp.R
import android.view.View
import com.example.lenny.studyresearchapp.model.Answer
import kotlinx.android.synthetic.main.answer_list.view.*
/**
 * Created by Lenny on 16/08/2017.
 */

class AnswerRecycleAdapter(val answerList: ArrayList<Answer>) : RecyclerView.Adapter<AnswerRecycleAdapter.ViewHolder>() {

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(answerList[position])
    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.answer_list, parent, false)
        return ViewHolder(v)
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {

        private var itemClickListener: ItemClickListener? = null

        override fun onClick(view: View) {
            itemClickListener!!.onClick(view, adapterPosition, false)
        }

        override fun onLongClick(view: View): Boolean {
            itemClickListener!!.onClick(view, adapterPosition, true)
            return true
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        fun bindItems(answer: Answer) {
            val textViewId = itemView.answer_id as TextView
            val textViewQuestion  = itemView.answer_question as TextView
            val textViewAnswer  = itemView.answer_answer as TextView

            textViewId.text = answer.answer_id
            textViewQuestion.text = answer.answer_question
            textViewAnswer.text = answer.answer_answer

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }
}
package com.example.lenny.studyresearchapp.Component

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lenny.studyresearchapp.R
import com.example.lenny.studyresearchapp.model.Diary
import kotlinx.android.synthetic.main.diary_list_item.view.*

/**
 * Created by Lenny on 17/08/2017.
 */

class DiaryRecycelAdapter(val diaryList: ArrayList<Diary>) :
        RecyclerView.Adapter<DiaryRecycelAdapter.ViewHolder>() {

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(diaryList[position])
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diary_list_item, parent, false)
        return ViewHolder(v)
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {

        override fun onClick(view: View) {
        }

        override fun onLongClick(view: View): Boolean {
            return true
        }

        fun bindItems(diary: Diary) {
            val title = itemView.diary_title as TextView
            val skill  = itemView.diary_skill as TextView
            val date  = itemView.diary_date as TextView
            val event  = itemView.diary_event as TextView

            title.text = diary.diary_title
            skill.text = diary.diary_skill
            date.text = diary.diary_date
            event.text = diary.diary_event

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }
}
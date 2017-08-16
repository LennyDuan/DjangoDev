package com.example.lenny.studyresearchapp.Component

import android.view.View
/**
 * Created by Lenny on 16/08/2017.
 */
interface ItemClickListener {
    fun onClick(view: View, position: Int, isLongClick: Boolean)
}
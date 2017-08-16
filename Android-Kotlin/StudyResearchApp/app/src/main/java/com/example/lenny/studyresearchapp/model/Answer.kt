package com.example.lenny.studyresearchapp.model

/**
 * Created by Lenny on 15/08/2017.
 */

data class Answer(
        var answer_id: String,
        var answer_question: String,
        var answer_before: String? = null,
        var answer_after: String? = null
)
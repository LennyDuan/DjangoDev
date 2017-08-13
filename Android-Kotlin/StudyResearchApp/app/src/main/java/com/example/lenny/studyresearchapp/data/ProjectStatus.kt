package com.example.lenny.studyresearchapp.data

/**
 * Created by Lenny on 13/08/2017.
 */

enum class ProjectStatus constructor(val value : Int) {
    NONE(0),
    INIT(1),
    ACCOUNT_DONE(2),
    PRE_QUESTIONNAIRE(3),
    DIARY(4),
    AFTER_QUESTIONNAIRE(5)
}
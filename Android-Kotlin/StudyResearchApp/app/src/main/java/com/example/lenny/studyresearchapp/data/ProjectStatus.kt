package com.example.lenny.studyresearchapp.data

/**
 * Created by Lenny on 13/08/2017.
 */

enum class ProjectStatus constructor(val guide : String) {
    NONE("Setting up the system..."),
    INIT("Hi kid!!! Would you like tell me about you?"),
    ACCOUNT_DONE("Hi kid, Wow you are a good child! Would you mind answering these questions?"),
    PRE_QUESTIONNAIRE("Hey, Don't For your questionnaire!!"),
    PRE_QUESTIONNAIRE_Done("Good Job, Lets' start writing the story!!!"),
    DIARY("Wow, do you want to tell me more about your story?"),
    DIARY_DONE("Good stories!!! Questionnaire time is comming !!!"),
    AFTER_QUESTIONNAIRE("Hey, Don't For last job. Good start with Good end!!"),
    AFTER_QUESTIONNAIRE_DONE("Thanks Kid, your helps change the world!!!")
}
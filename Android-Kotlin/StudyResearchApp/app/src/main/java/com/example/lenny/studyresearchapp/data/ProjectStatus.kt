package com.example.lenny.studyresearchapp.data

/**
 * Created by Lenny on 13/08/2017.
 */

enum class ProjectStatus constructor(val guide : String) {
    NONE("Setting up the system..."),
    INIT("Please tell me about you?"),
    ACCOUNT_DONE("Hi, there are really important information! Would you mind answering these questions?"),
    PRE_QUESTIONNAIRE("Please don't Forget your questionnaire!"),
    PRE_QUESTIONNAIRE_Done("Well done! Lets' start writing the diary stories!"),
    DIARY("Please keep recording your story until the end date?"),
    DIARY_DONE("Good stories! Same questionnaire is coming again!"),
    AFTER_QUESTIONNAIRE("Please finish the last job. Good start with Good end!"),
    AFTER_QUESTIONNAIRE_DONE("Thanks, your work helps a lot!")
}
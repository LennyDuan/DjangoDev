package com.example.lenny.studyresearchapp.data

/**
 * Created by Lenny on 13/08/2017.
 */
enum class ProjectAPI constructor(val url : String) {
    // GET API url
    GET_STUDY_LIST_URL("http://10.0.2.2:8000/polls/api/v1/study/"),
    // POST API url
    POST_FEEDBACK_URL("http://10.0.2.2:8000/polls/api/v1/post/feedback/")
}
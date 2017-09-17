package com.example.lenny.studyresearchapp.data

/**
 * Created by Lenny on 13/08/2017.
 */
enum class ProjectAPI constructor(val url : String) {
    // GET API url
    GET_ACTIVE_STUDY_URL("http://10.0.2.2:8000/polls/api/v1/study/isActive"),
    GET_Question_List_URL("http://10.0.2.2:8000/polls/api/v1/question/"),
    GET_SKILLS_LIST_URL("http://10.0.2.2:8000/polls/api/v1/skill/"),

    // POST API url
    POST_FEEDBACK_URL("http://10.0.2.2:8000/polls/api/v1/post/feedback/"),
    POST_USER_INFO("http://10.0.2.2:8000/polls/api/v1/post/userInfo/"),
    POST_ANSWERS_TO_FEEDBACK("http://10.0.2.2:8000/polls/api/v1/post/answer/"),
    POST_DIARY_USERID("http://10.0.2.2:8000/polls/api/v1/post/userInfoid/"),

    // DELETE API url
    DELETE_FEEDBACK_URL("http://10.0.2.2:8000/polls/api/v1/delete/feedback/"),
    DELETE_USER_INFO_URL("http://10.0.2.2:8000/polls/api/v1/delete/userInfo/"),
}
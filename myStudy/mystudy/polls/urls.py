from django.conf.urls import url
from . import views
from .api.v1.get import diary, userInfo, feedback, question
from .api.v1.post import questionP, diaryP, feedbackP, userInfoP
from .api.v1.delete import feedbackD, userinfoD
#from .view import views
app_name = 'polls'
urlpatterns = [

    url(r'^$', views.index, name='index'),
    ## Restful API GET/POST/DELETE

################# DELETE API ##############
    url(r'^api/v1/delete/feedback/(?P<feedback_id>.+)$', feedbackD.feedbackDelete, name='feedback_delete_ID'),
    url(r'^api/v1/delete/userinfo/(?P<userInfo_id>.+)$', userinfoD.userinfoDelete, name='userinfo_delete_ID'),

################# POST API ##############
    # POST Question/Questionnaire
    url(r'^api/v1/post/questionnaire/$', questionP.questionnairePost, name='questionnaire_post'),
    url(r'^api/v1/post/questionnaire/(?P<pk_id>[0-9]+)/study$', questionP.studyPost, name='study_post'),
    url(r'^api/v1/post/questionnaire/(?P<pk_id>[0-9]+)/question$', questionP.questionPost, name='question_post'),

    # POST Diary
    url(r'^api/v1/post/userInfopk/(?P<pk_id>[0-9]+)/diary$', diaryP.diaryPost, name='diary_post'),
    url(r'^api/v1/post/userInfoEmail/(?P<userInfo_email>.+)/diary$', diaryP.diaryPostUserEmail, name='diary_post_userinfoEmail'),
    url(r'^api/v1/post/userInfoid/(?P<userInfo_id>.+)/diary$', diaryP.diaryPostID, name='diary_post_userinfoID'),

    # POST UserInfo
    url(r'^api/v1/post/userInfo/(?P<study_pkid>[0-9]+)/study/(?P<feedback_pkid>[0-9]+)/feedback/$', userInfoP.userInfoPost, name='userInfo_post'),
    url(r'^api/v1/post/userInfo/(?P<study_field>.+)/studyField/(?P<feedback_id>.+)/feedbackID/$', userInfoP.userInfoPostID, name='userInfo_post_viaID'),
    url(r'^api/v1/post/userInfo/(?P<pk_id>[0-9]+)/update$', userInfoP.userInfoPostUpdate, name='userInfo_postUpdate'),

    # POST Feedback/Answer
    url(r'^api/v1/post/feedback/$', feedbackP.feedbackPost, name='feedback_post'),
    url(r'^api/v1/post/feedback/(?P<pk_id>[0-9]+)/update$', feedbackP.feedbackPostUpdate, name='feedback_postUpdate'),
    url(r'^api/v1/post/answer/(?P<feedback_id>.+)/feedback$', feedbackP.answerPost, name='answer_post_id'),
    url(r'^api/v1/post/answer/(?P<pk_id>[0-9]+)/update$', feedbackP.answerPostUpdate, name='answer_postUpdate'),
    url(r'^api/v1/post/answer/(?P<answer_id>.+)/updateID$', feedbackP.answerPostUpdateViaIDAfter, name='answer_postUpdate_ID'),

################# GET API  ##############

    # diary GET api
    url(r'^api/v1/diary/$', diary.all, name='diary_all'),
    url(r'^api/v1/diary/(?P<pk_id>[0-9]+)/$', diary.detail, name='diary_detail'),
    url(r'^api/v1/diary/(?P<diary_id>[0-9]+)/diary_id/$', diary.detailDiaryID, name='diary_detail_diaryID'),
    # TODO - change email to id
    url(r'^api/v1/diary/user/(?P<userInfo_email>.+)/$', diary.detailuserInfoEmail, name='diary_userInfoEmail'),
    url(r'^api/v1/skill/$', diary.all_skill, name="skill_all"),

    # userInfo GET api
    url(r'^api/v1/userInfo/$', userInfo.userInfoAll, name='userInfo_all'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)$', userInfo.userInfoDetail, name='userInfo_detail'),
    # url(r'^api/v1/userInfo/(?P<userInfo_email>.+)/userInfo_email/$', userInfo.detailuserInfoEmail, name='userInfo_userInfoEmail'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/study$', userInfo.userInfoStudy, name='userInfo_study'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/feedback$', userInfo.userInfoFeedback, name='userInfo_feedback'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/answers$', userInfo.userInfoAnswer, name='userInfo_answer'),

    # feedback/answer GET api
    url(r'^api/v1/feedback/$', feedback.feedbackAll, name='feedback_all'),
    url(r'^api/v1/feedback/(?P<pk_id>[0-9]+)/$', feedback.feedbackDetail, name='feedback_detail'),

    url(r'^api/v1/answer/$', feedback.answerAll, name='answer_all'),
    url(r'^api/v1/answer/(?P<pk_id>[0-9]+)/$', feedback.answerDetail, name='answer_detail'),
    url(r'^api/v1/answer/(?P<feedback_id>[0-9]+)/feedback$', feedback.answerFeedback, name='answer_feedback_list'),

    # study/questionnaire/question GET api
    url(r'^api/v1/study/$', question.studyAll, name='study_all'),
    url(r'^api/v1/study/isActive$', question.studyActive, name='study_is_actives'),
    url(r'^api/v1/study/(?P<pk_id>[0-9]+)/$', question.studyDetail, name='study_detail'),
    url(r'^api/v1/study/(?P<study_field>.+)/field$', question.studyDetailByField, name='study_detail'),

    url(r'^api/v1/questionnaire/$', question.questionnaireAll, name='questionnaire_all'),
    url(r'^api/v1/questionnaire/(?P<pk_id>[0-9]+)/$', question.questionnaireDetail, name='questionnaire_detail'),
    url(r'^api/v1/questionnaire/(?P<study_field>.+)/field$', question.questionnaireDetailByField, name='questionnaire_detail'),

    url(r'^api/v1/question/$', question.questionAll, name='question_all'),
    url(r'^api/v1/question/(?P<pk_id>[0-9]+)/$', question.questionDetail, name='question_detail'),
    url(r'^api/v1/question/(?P<study_field>.+)/field$', question.questionDetailByField, name='question_detail_field_list'),
]

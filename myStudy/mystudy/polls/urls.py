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
    url(r'^api/v1/post/index/$', questionP.index, name='post_index'),
    url(r'^api/v1/post/test/$', questionP.testPost, name='test_post'),

    # POST Question/Questionnaire
    url(r'^api/v1/post/questionnaire/$', questionP.questionnairePost, name='questionnaire_post'),
    url(r'^api/v1/post/study/(?P<pk_id>[0-9]+)/questionnaire/$', questionP.studyPost, name='study_post'),
    url(r'^api/v1/post/question/(?P<pk_id>[0-9]+)/questionnaire/$', questionP.questionPost, name='question_post'),

    # POST Diary
    url(r'^api/v1/post/diary/(?P<pk_id>[0-9]+)/userInfopk/$', diaryP.diaryPost, name='diary_post'),
    url(r'^api/v1/post/diary/(?P<userInfo_email>.+)/userInfoEmail/$', diaryP.diaryPostUserEmail, name='diary_post_userinfoEmail'),

    # POST UserInfo
    url(r'^api/v1/post/userInfo/(?P<study_pkid>[0-9]+)/study/(?P<feedback_pkid>[0-9]+)/feedback/$', userInfoP.userInfoPost, name='userInfo_post'),
    url(r'^api/v1/post/userInfo/(?P<study_field>.+)/studyField/(?P<feedback_id>.+)/feedbackID/$', userInfoP.userInfoPostID, name='userInfo_post_viaID'),
    url(r'^api/v1/post/userInfo/(?P<pk_id>[0-9]+)/update/$', userInfoP.userInfoPostUpdate, name='userInfo_postUpdate'),

    # POST Feedback/Answer
    url(r'^api/v1/post/feedback/$', feedbackP.feedbackPost, name='feedback_post'),
    url(r'^api/v1/post/feedback/(?P<pk_id>[0-9]+)/update/$', feedbackP.feedbackPostUpdate, name='feedback_postUpdate'),
    url(r'^api/v1/post/answer/(?P<feedback_id>.+)/feedback/$', feedbackP.answerPost, name='answer_post_id'),
    url(r'^api/v1/post/answer/(?P<pk_id>[0-9]+)/update/$', feedbackP.answerPostUpdate, name='answer_postUpdate'),
    url(r'^api/v1/post/answer/(?P<answer_id>.+)/updateID/$', feedbackP.answerPostUpdateViaIDAfter, name='answer_postUpdate_ID'),

################# GET API  ##############

    # diary GET api
    url(r'^api/v1/index/diary/$', diary.index, name='diary_index'),
    url(r'^api/v1/diary/$', diary.all, name='diary_all'),
    url(r'^api/v1/diary/(?P<pk_id>[0-9]+)/$', diary.detail, name='diary_detail'),
    url(r'^api/v1/diary/(?P<diary_id>[0-9]+)/diary_id/$', diary.detailDiaryID, name='diary_detail_diaryID'),
    url(r'^api/v1/diary/(?P<userInfo_email>.+)/userInfo_email/$', diary.detailuserInfoEmail, name='diary_userInfoEmail'),
    url(r'^api/v1/skill/$', diary.all_skill, name="skill_all"),

    # userInfo GET api
    url(r'^api/v1/index/userInfo/$', userInfo.index, name='userInfo_index'),
    url(r'^api/v1/userInfo/$', userInfo.userInfoAll, name='userInfo_all'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/$', userInfo.userInfoDetail, name='userInfo_detail'),
    url(r'^api/v1/userInfo/(?P<userInfo_email>.+)/userInfo_email/$', userInfo.detailuserInfoEmail, name='userInfo_userInfoEmail'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/study$', userInfo.userInfoStudy, name='userInfo_study'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/feedback$', userInfo.userInfoFeedback, name='userInfo_feedback'),
    url(r'^api/v1/userInfo/(?P<pk_id>[0-9]+)/answer$', userInfo.userInfoAnswer, name='userInfo_answer'),

    # feedback/answer GET api
    url(r'^api/v1/index/feedback/$', feedback.index, name='feedback_index'),
    url(r'^api/v1/feedback/$', feedback.feedbackAll, name='feedback_all'),
    url(r'^api/v1/feedback/(?P<pk_id>[0-9]+)/$', feedback.feedbackDetail, name='feedback_detail'),

    url(r'^api/v1/answer/$', feedback.answerAll, name='answer_all'),
    url(r'^api/v1/answer/(?P<pk_id>[0-9]+)/$', feedback.answerDetail, name='answer_detail'),
    url(r'^api/v1/answer/(?P<feedback_id>[0-9]+)/feedback$', feedback.answerFeedback, name='answer_feedback_list'),

    # study/questionnaire/question GET api
    url(r'^api/v1/index/study/$', question.index, name='study_index'),
    url(r'^api/v1/study/$', question.studyAll, name='study_all'),
    url(r'^api/v1/study/(?P<pk_id>[0-9]+)/$', question.studyDetail, name='study_detail'),
    url(r'^api/v1/study/(?P<study_field>.+)/field$', question.studyDetailByField, name='study_detail'),

    url(r'^api/v1/questionnaire/$', question.questionnaireAll, name='questionnaire_all'),
    url(r'^api/v1/questionnaire/(?P<pk_id>[0-9]+)/$', question.questionnaireDetail, name='questionnaire_detail'),
    url(r'^api/v1/questionnaire/(?P<study_field>.+)/field$', question.questionnaireDetailByField, name='questionnaire_detail'),

    url(r'^api/v1/question/$', question.questionAll, name='question_all'),
    url(r'^api/v1/question/(?P<pk_id>[0-9]+)/$', question.questionDetail, name='question_detail'),
    url(r'^api/v1/question/(?P<study_field>.+)/field$', question.questionDetailByField, name='question_detail_field_list'),

    # url(r'^(?P<question_id>[0-9]+)/$', views.detail, name='detail'),
    # ex: /polls/5/results/
    # url(r'^(?P<question_id>[0-9]+)/results/$', views.results, name='results'),
    # ex: /polls/5/vote/
    # url(r'^(?P<question_id>[0-9]+)/vote/$', views.vote, name='vote'),

    # url(r'^$', views.IndexView.as_view(), name='index'),
    # url(r'^(?P<pk>[0-9]+)/$', views.DetailView.as_view(), name='detail'),
    # url(r'^(?P<pk>[0-9]+)/results/$', views.ResultsView.as_view(), name='results'),
    # url(r'^(?P<question_id>[0-9]+)/vote/$', views.vote, name='vote'),
]

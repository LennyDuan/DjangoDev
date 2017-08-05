from django.conf.urls import url
from . import views
from .api.v1.get import diary, userInfo, feedback, question
from .api.v1.post import questionP, diaryP, feedbackP, userInfoP
#from .view import views
app_name = 'polls'
urlpatterns = [

    url(r'^$', views.index, name='index'),

    ## Restful API GET/POST
################# POST API ##############
    url(r'^api/v1/post/index/$', questionP.index, name='post_index'),
    url(r'^api/v1/post/test/$', questionP.testPost, name='test_post'),

    # POST Question/Questionnaire
    url(r'^api/v1/post/questionnaire/$', questionP.questionnairePost, name='questionnaire_post'),
    url(r'^api/v1/post/study/(?P<pk_id>[0-9]+)/questionnaire/$', questionP.studyPost, name='study_post'),
    url(r'^api/v1/post/question/(?P<pk_id>[0-9]+)/questionnaire/$', questionP.questionPost, name='question_post'),

    # POST Diary
    url(r'^api/v1/post/diary/$', diaryP.diaryPost, name='diary_post'),

    # POST UserInfo
    url(r'^api/v1/post/userInfo/$', userInfoP.userInfoPost, name='userInfo_post'),

    # POST Feedback/Answer
    url(r'^api/v1/post/feedback/$', feedbackP.feedbackPost, name='feedback_post'),
    url(r'^api/v1/post/answer/$', feedbackP.answerPost, name='answer_post'),

################# GET API  ##############

    # diary GET api
    url(r'^api/v1/index/diary/$', diary.index, name='diary_index'),
    url(r'^api/v1/diary/$', diary.all, name='diary_all'),
    url(r'^api/v1/diary/(?P<pk_id>[0-9]+)/$', diary.detail, name='diary_detail'),
    url(r'^api/v1/diary/(?P<diary_id>[0-9]+)/diary_id/$', diary.detailDiaryID, name='diary_detail_diaryID'),
    url(r'^api/v1/diary/(?P<userInfo_email>.+)/userInfo_email/$', diary.detailuserInfoEmail, name='diary_userInfoEmail'),

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
    url(r'^api/v1/question/(?P<study_field>.+)/field$', question.questionDetailByField, name='question_detail'),

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

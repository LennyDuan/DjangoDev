from django.conf.urls import url
from . import views
from .api.v1 import diary, user, feedback, question
#from .view import views
app_name = 'polls'
urlpatterns = [

    url(r'^$', views.index, name='index'),
    # diary api
    url(r'^api/v1/index/diary/$', diary.index, name='diary_index'),
    url(r'^api/v1/diary/$', diary.all, name='diary_all'),
    url(r'^api/v1/diary/(?P<pk_id>[0-9]+)/$', diary.detail, name='diary_detail'),
    url(r'^api/v1/diary/(?P<diary_id>[0-9]+)/diary_id/$', diary.detailDiaryID, name='diary_detail_diaryID'),
    url(r'^api/v1/diary/(?P<user_email>.+)/user_email/$', diary.detailUserEmail, name='diary_userEmail'),

    # user api
    url(r'^api/v1/index/user/$', user.index, name='user_index'),
    url(r'^api/v1/user/$', user.userAll, name='user_all'),
    url(r'^api/v1/user/(?P<pk_id>[0-9]+)/$', user.userDetail, name='user_detail'),
    url(r'^api/v1/user/(?P<user_email>.+)/user_email/$', user.detailUserEmail, name='user_userEmail'),
    url(r'^api/v1/user/(?P<pk_id>[0-9]+)/study$', user.userStudy, name='user_study'),
    url(r'^api/v1/user/(?P<pk_id>[0-9]+)/feedback$', user.userFeedback, name='user_feedback'),
    url(r'^api/v1/user/(?P<pk_id>[0-9]+)/answer$', user.userAnswer, name='user_answer'),

    # feedback/answer api
    url(r'^api/v1/index/feedback/$', feedback.index, name='feedback_index'),
    url(r'^api/v1/feedback/$', feedback.feedbackAll, name='feedback_all'),
    url(r'^api/v1/feedback/(?P<pk_id>[0-9]+)/$', feedback.feedbackDetail, name='feedback_detail'),

    url(r'^api/v1/answer/$', feedback.answerAll, name='answer_all'),
    url(r'^api/v1/answer/(?P<pk_id>[0-9]+)/$', feedback.answerDetail, name='answer_detail'),
    url(r'^api/v1/answer/(?P<feedback_id>[0-9]+)/feedback$', feedback.answerFeedback, name='answer_feedback_list'),

    # study/questionnaire/question api
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

from django.conf.urls import url
from django.conf import settings
from django.conf.urls.static import static

from . import views, api_views


urlpatterns = [
    # Regular Django Views
    url(r'^$', views.index, name='index'),
    url(r'^(?P<pk>[0-9]+)/$', views.DetailView.as_view(), name='detail'),
    url(r'^(?P<pk>[0-9]+)/results/$', views.ResultsView.as_view(), name='results'),
    url(r'^(?P<question_id>[0-9]+)/vote/$', views.vote, name='vote'),

    # API views
    url(r'^api/questions/$', api_views.QuestionList.as_view()),
    url(r'^api/questions/(?P<pk>[0-9]+)/$', api_views.QuestionDetail.as_view()),
    url(r'^api/choices/(?P<pk>[0-9]+)/$', api_views.ChoiceDetail.as_view()),
    url(r'^api/create_user/$', api_views.UserCreate.as_view()),
    url(r'^api/users/(?P<pk>[0-9]+)/$', api_views.UserDetail.as_view()),
] + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)

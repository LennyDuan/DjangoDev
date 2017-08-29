from django.conf.urls import include, url
from django.contrib import admin


urlpatterns = [
    # Regular Django urls
    url(r'^polls/',    include('polls.urls', namespace="polls")),
    url(r'^accounts/', include('accounts.urls', namespace="accounts")),
    url(r'^admin/', include(admin.site.urls)),
]

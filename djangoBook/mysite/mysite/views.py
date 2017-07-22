from django.http import HttpResponse
from datetime import datetime
from datetime import timedelta

def current_datetime(request):
    now = datetime.now()
    html = "<html><body>It is now %s.</body></html>" % now
    return HttpResponse(html)

def diff_datetime(request, param):
    hour = int(param)
    diff_date = datetime.now() + timedelta(hours=hour)
    html = "<html><body>It is now %s.</body></html>" % diff_date
    return HttpResponse(html)

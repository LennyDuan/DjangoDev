from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import Diary, UserInfo
from django.views.decorators.csrf import csrf_exempt
import json

# POST UserInfo
@csrf_exempt
def userInfoPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # UserInfo model Save
        return HttpResponse("it was UserInfo post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

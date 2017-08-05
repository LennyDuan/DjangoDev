from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import Diary, UserInfo
from django.views.decorators.csrf import csrf_exempt
import json

# POST Questionnaire
@csrf_exempt
def diaryPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # Diary model Save
        return HttpResponse("it was Diary post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

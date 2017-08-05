from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Feedback, Answer, Study
from django.views.decorators.csrf import csrf_exempt
import json

# POST Feedback
@csrf_exempt
def feedbackPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # Feedback model Save
        return HttpResponse("it was Feedback post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

# POST Answer
@csrf_exempt
def answerPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # Feedback model Save
        return HttpResponse("it was Answer post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

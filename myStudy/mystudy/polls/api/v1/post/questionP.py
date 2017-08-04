from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Questionnaire, Question, Study
from django.views.decorators.csrf import csrf_exempt
import json

# Create Questionnaire & Question API POST
def index(request):
    return HttpResponse("Hello, welcome to Study/Question api POST index page!")

# POST Questionnaire List
@csrf_exempt
def questionnairePost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        return HttpResponse("it was post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

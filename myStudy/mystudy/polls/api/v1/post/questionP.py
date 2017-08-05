from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Questionnaire, Question, Study
from django.views.decorators.csrf import csrf_exempt
import json

# Create Questionnaire & Question API POST
def index(request):
    return HttpResponse("Hello, welcome to Study/Question api POST index page!")

# POST TEST
@csrf_exempt
def testPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        return HttpResponse("it was post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")


# POST Questionnaire
@csrf_exempt
def questionnairePost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        try:
            body = json.loads(received_data)
            # Questionnaire model Save
            questionnaire_id = body["questionnaire_id"]
            questionnaire_text = body["questionnaire_text"]
            questionnaire_field = body["questionnaire_field"]
            questionnaire = Questionnaire(
                questionnaire_id=questionnaire_id,
                questionnaire_text=questionnaire_text,
                questionnaire_field=questionnaire_field
            )
            questionnaire.save()
            return HttpResponse("it was Questionnaire post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("Post Failed!!")

# POST Study
@csrf_exempt
def studyPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # Study model Save
        return HttpResponse("it was a Study post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

# POST Question
@csrf_exempt
def questionPost(request):
    if request.method == 'POST':
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        # Question model Save
        return HttpResponse("it was a Question post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

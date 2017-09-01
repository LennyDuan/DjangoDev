from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Questionnaire, Question, Study
from django.views.decorators.csrf import csrf_exempt
import json

# Create Questionnaire & Question API POST
def index(request):
    return HttpResponse("Hello, welcome to Study/Question api POST index page!")

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
    return HttpResponse("This is the POST api!!")

# POST Study
@csrf_exempt
def studyPost(request, pk_id):
    if request.method == 'POST':
        questionnaire = get_object_or_404(Questionnaire, pk=pk_id)
        try:
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            study_id = body["study_id"]
            study_field = questionnaire.questionnaire_field
            study_owner = body["study_owner"]
            study_questionnaire = questionnaire
            study = Study(
                study_id=study_id,
                study_field=study_field,
                study_owner=study_owner,
                study_questionnaire=study_questionnaire
            )
            # Study model Save
            study.save()
            return HttpResponse("it was a Study post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("This is the POST api!!")

# POST Question
@csrf_exempt
def questionPost(request, pk_id):
    if request.method == 'POST':
        questionnaire = get_object_or_404(Questionnaire, pk=pk_id)
        try:
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            question_id = body["question_id"]
            question_text = body["question_text"]
            question_questionnaire = questionnaire
            question = Question(
                question_id=question_id,
                question_text=question_text,
                question_questionnaire=question_questionnaire
            )
            # Question model Save
            question.save()
            return HttpResponse("it was a Question post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))

    return HttpResponse("This is the POST api!!")

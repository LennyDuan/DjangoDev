from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Questionnaire, Question, Study
import json

# Create Questionnaire & Question API
def index(request):
    return HttpResponse("Hello, welcome to Study/Question api index page!")

# Get Study List
#@csrf_exempt
def studyAll(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_study_list = Study.objects.all()
    all_study_serialized = serializers.serialize('json', all_study_list)
    return JsonResponse(all_study_serialized, safe=False)

# Get Study by pk id
def studyDetail(request, pk_id):
    study = get_object_or_404(Study, pk=pk_id)
    study_serialized = serializers.serialize('json', [study])
    return JsonResponse(study_serialized, safe=False)

# Get Study by study_field
def studyDetailByField(request, study_field):
    study = get_object_or_404(Study, study_field=study_field)
    study_serialized = serializers.serialize('json', [study])
    return JsonResponse(study_serialized, safe=False)

# Get Questionnaire List
#@csrf_exempt
def questionnaireAll(request):
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_questionnaire_list = Questionnaire.objects.all()
    all_questionnaire_serialized = serializers.serialize('json', all_questionnaire_list)
    return JsonResponse(all_questionnaire_serialized, safe=False)

# Get Study by pk id
def questionnaireDetail(request, pk_id):
    questionnaire = get_object_or_404(Questionnaire, pk=pk_id)
    questionnaire_serialized = serializers.serialize('json', [questionnaire])
    return JsonResponse(questionnaire_serialized, safe=False)

# Get Study by study_field
def questionnaireDetailByField(request, study_field):
    study = get_object_or_404(Study, study_field=study_field)
    questionnaire = study.study_questionnaire
    questionnaire_serialized = serializers.serialize('json', [questionnaire])
    return JsonResponse(questionnaire_serialized, safe=False)

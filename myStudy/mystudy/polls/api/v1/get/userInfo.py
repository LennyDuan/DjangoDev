from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Feedback, Answer, Study
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to UserInfo api index page!")

# Get UserInfo List
#@csrf_exempt
def userInfoAll(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_UserInfo_list = UserInfo.objects.all()
    all_UserInfo_serialized = serializers.serialize('json', all_UserInfo_list)
    return JsonResponse(all_UserInfo_serialized, safe=False)

# Get UserInfo by pk id
def userInfoDetail(request, pk_id):
    userInfo = get_object_or_404(UserInfo, pk=pk_id)
    userInfo_serialized = serializers.serialize('json', [userInfo])
    return JsonResponse(userInfo_serialized, safe=False)

# Get UserInfo by UserInfo_email
def detailuserInfoEmail(request, userInfo_email):
    userInfo = get_object_or_404(UserInfo, userInfo_email=userInfo_email)
    userInfo_serialized = serializers.serialize('json', [userInfo])
    return JsonResponse(userInfo_serialized, safe=False)

# Get UserInfo's Study by pk id
def userInfoStudy(request, pk_id):
    userInfo = get_object_or_404(UserInfo, pk=pk_id)
    study = userInfo.userInfo_study
    study_serialized = serializers.serialize('json', [study])
    return JsonResponse(study_serialized, safe=False)

# Get UserInfo's Feedback by pk id
def userInfoFeedback(request, pk_id):
    userInfo = get_object_or_404(UserInfo, pk=pk_id)
    feedback = userInfo.userInfo_feedback
    feedback_serialized = serializers.serialize('json', [feedback])
    return JsonResponse(feedback_serialized, safe=False)

# Get UserInfo's Answers by pk id
def userInfoAnswer(request, pk_id):
    userInfo = get_object_or_404(UserInfo, pk=pk_id)
    feedback = userInfo.userInfo_feedback
    userInfo_answer_list = Answer.objects.all().filter(answer_feedback=feedback)
    userInfo_answer_serialized = serializers.serialize('json', userInfo_answer_list)
    return JsonResponse(userInfo_answer_serialized, safe=False)

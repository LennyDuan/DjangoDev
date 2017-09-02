from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import Diary, UserInfo, Skill
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API

# Get Diary List
#@csrf_exempt
def all(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_diary_list = Diary.objects.all()
    all_diary_serialized = serializers.serialize('json', all_diary_list)
    return JsonResponse(all_diary_serialized, safe=False)

# Get Diary by pk id
def detail(request, pk_id):
    diary = get_object_or_404(Diary, pk=pk_id)
    diary_serialized = serializers.serialize('json', [diary])
    return JsonResponse(diary_serialized, safe=False)

# Get Diary by diary_id
def detailDiaryID(request, diary_id):
    diary = get_object_or_404(Diary, diary_id=diary_id)
    diary_serialized = serializers.serialize('json', [diary])
    return JsonResponse(diary_serialized, safe=False)

# Get Diary List by user_email
def detailuserInfoEmail(request, userInfo_email):
    userInfo = get_object_or_404(UserInfo, userInfo_email=userInfo_email)
    userInfo_diary_list = Diary.objects.all().filter(diary_userInfo=userInfo)
    userInfo_diary_serialized = serializers.serialize('json', userInfo_diary_list)
    return JsonResponse(userInfo_diary_serialized, safe=False)

# Get Diary List
#@csrf_exempt
def all_skill(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_skill_list = Skill.objects.all()
    all_skill_serialized = serializers.serialize('json', all_skill_list)
    return JsonResponse(all_skill_serialized, safe=False)

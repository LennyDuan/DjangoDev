from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import Diary, UserInfo
from django.views.decorators.csrf import csrf_exempt
import json

# POST Diary
@csrf_exempt
def diaryPost(request, pk_id):
    if request.method == 'POST':
        userInfo = get_object_or_404(UserInfo, pk=pk_id)
        try:
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            diary_id = body["diary_id"]
            diary_skill = body["diary_skill"]
            diary_title = body["diary_title"]
            diary_detail = body["diary_detail"]
            diary_userInfo = userInfo
            diary = Diary(
                diary_id=diary_id,
                diary_skill=diary_skill,
                diary_title=diary_title,
                diary_detail=diary_detail,
                diary_userInfo=diary_userInfo,
            )
            # Question model Save
            diary.save()
            return HttpResponse("it was a Diary post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))

    return HttpResponse("This is the POST api!!")

@csrf_exempt
def diaryPostUserEmail(request, userInfo_email):
    if request.method == 'POST':
        userInfo = get_object_or_404(UserInfo, userInfo_email=userInfo_email)
        try:
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            diary_id = body["diary_id"]
            diary_skill = body["diary_skill"]
            diary_title = body["diary_title"]
            diary_detail = body["diary_detail"]
            diary_latitude = body["diary_latitude"]
            diary_longitude = body["diary_longitude"]

            diary_userInfo = userInfo
            diary = Diary(
                diary_id=diary_id,
                diary_skill=diary_skill,
                diary_title=diary_title,
                diary_detail=diary_detail,
                diary_userInfo=diary_userInfo,
                diary_latitude=diary_latitude,
                diary_longitude=diary_longitude,
            )
            # Question model Save
            diary.save()
            return HttpResponse("it was a Diary post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))

    return HttpResponse("This is the POST api!!")

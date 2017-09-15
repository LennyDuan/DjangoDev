from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import Diary, UserInfo, Feedback, Study
from django.views.decorators.csrf import csrf_exempt
import json

# POST UserInfo
@csrf_exempt
def userInfoPost(request, study_pkid, feedback_pkid):
    if request.method == 'POST':
        study = get_object_or_404(Study, pk=study_pkid)
        feedback = get_object_or_404(Feedback, pk=feedback_pkid)

        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        userInfo_id = body["userInfo_id"]
        userInfo_age = body["userInfo_age"]
        userInfo_gender = body["userInfo_gender"]
        userInfo_start_date = body["userInfo_start_date"]
        userInfo_end_date = body["userInfo_end_date"]
        userInfo = UserInfo(
            userInfo_id=userInfo_id,
            userInfo_age=userInfo_age,
            userInfo_gender=userInfo_gender,
            userInfo_start_date=userInfo_start_date,
            userInfo_end_date=userInfo_end_date,
            userInfo_study=study,
            userInfo_feedback=feedback
        )
        # Answer model save
        userInfo.save()
        # UserInfo model Save
        return HttpResponse("it was UserInfo post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

# POST UserInfo via UniqueID
@csrf_exempt
def userInfoPostID(request, study_field, feedback_id):
    if request.method == 'POST':
        study = get_object_or_404(Study, study_field=study_field)
        feedback = get_object_or_404(Feedback, feedback_id=feedback_id)

        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        userInfo_id = body["userInfo_id"]
        userInfo_age = body["userInfo_age"]
        userInfo_gender = body["userInfo_gender"]
        userInfo_start_date = body["userInfo_start_date"]
        userInfo_end_date = body["userInfo_end_date"]
        userInfo = UserInfo(
            userInfo_id=userInfo_id,
            userInfo_age=userInfo_age,
            userInfo_gender=userInfo_gender,
            userInfo_start_date=userInfo_start_date,
            userInfo_end_date=userInfo_end_date,
            userInfo_study=study,
            userInfo_feedback=feedback
        )
        # Answer model save
        userInfo.save()
        # UserInfo model Save
        return HttpResponse("it was UserInfo post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")


# POST UserInfo
@csrf_exempt
def userInfoPostUpdate(request, pk_id):
    if request.method == 'POST':
        userInfo = get_object_or_404(UserInfo, pk=pk_id)
        received_data = request.body.decode("utf-8")
        body = json.loads(received_data)
        userInfo_id = body["userInfo_id"]
        userInfo_age = body["userInfo_age"]
        userInfo_gender = body["userInfo_gender"]
        userInfo_start_date = body["userInfo_start_date"]
        userInfo_end_date = body["userInfo_end_date"]

        userInfo.userInfo_id=userInfo_id
        userInfo.userInfo_age=userInfo_age
        userInfo.userInfo_gender=userInfo_gender
        userInfo.userInfo_start_date=userInfo_start_date
        userInfo.userInfo_end_date=userInfo_end_date

        # Answer model save
        userInfo.save()
        # UserInfo model Save
        return HttpResponse("it was UserInfo post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

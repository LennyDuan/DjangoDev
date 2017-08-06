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
        userInfo_email = body["userInfo_email"]
        userInfo_name = body["userInfo_name"]
        userInfo_start_date = body["userInfo_start_date"]
        userInfo_end_date = body["userInfo_end_date"]
        userInfo = UserInfo(
            userInfo_id=userInfo_id,
            userInfo_email=userInfo_email,
            userInfo_name=userInfo_name,
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
        userInfo_email = body["userInfo_email"]
        userInfo_name = body["userInfo_name"]
        userInfo_start_date = body["userInfo_start_date"]
        userInfo_end_date = body["userInfo_end_date"]

        userInfo.userInfo_id=userInfo_id
        userInfo.userInfo_email=userInfo_email
        userInfo.userInfo_name=userInfo_name
        userInfo.userInfo_start_date=userInfo_start_date
        userInfo.userInfo_end_date=userInfo_end_date

        # Answer model save
        userInfo.save()
        # UserInfo model Save
        return HttpResponse("it was UserInfo post request!!__: " + json.dumps(body))

    return HttpResponse("Post Failed!!")

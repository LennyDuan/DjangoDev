from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo
from django.views.decorators.csrf import csrf_exempt
#from django.views.decorators.csrf import csrf_exempt

# Delete Feedback by id
@csrf_exempt
def userinfoDelete(request, userInfo_id):
    userInfo = get_object_or_404(UserInfo, userInfo_id=userInfo_id)
    userInfo.delete()
    return HttpResponse("This is the DELETE api!!")

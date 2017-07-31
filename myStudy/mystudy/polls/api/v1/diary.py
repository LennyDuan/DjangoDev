from django.shortcuts import render, get_object_or_404
from django.urls import reverse
from django.views import generic
from django.http import Http404, HttpResponse, HttpResponseRedirect, JsonResponse
from django.core import serializers
from polls.models import Diary, User
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to diary api index page!")

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
def detailUserEmail(request, user_email):
    user = get_object_or_404(User, user_email=user_email)
    user_diary_list = Diary.objects.all().filter(diary_user=user)
    user_diary_serialized = serializers.serialize('json', user_diary_list)
    return JsonResponse(user_diary_serialized, safe=False)

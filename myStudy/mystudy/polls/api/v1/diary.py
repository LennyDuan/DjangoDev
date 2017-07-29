from django.shortcuts import render, get_object_or_404
from django.urls import reverse
from django.views import generic
from django.http import Http404, HttpResponse, HttpResponseRedirect, JsonResponse
from django.core import serializers
from polls.models import Diary, User

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to diary api index page!")

# Get Diary List
def all(request):
    all_diary_list = Diary.objects.all()
    all_diary_serialized = serializers.serialize('json', all_diary_list)
    return JsonResponse(all_diary_serialized, safe=False)

# Get Diary by pk id
def detail(request, pk_id):
    try:
        diary = Diary.objects.get(pk=pk_id)
        diary_serialized = serializers.serialize('json', [diary])
    except Diary.DoesNotExist:
        raise Http404("Diary does not exist")
    return JsonResponse(diary_serialized, safe=False)

# Get Diary by diary_id
def detailDiaryID(request, diary_id):
    try:
        diary = Diary.objects.get(diary_id=diary_id)
        diary_serialized = serializers.serialize('json', [diary])
    except Diary.DoesNotExist:
        raise Http404("Diary does not exist")

    return JsonResponse(diary_serialized, safe=False)

# Get Diary List by user_email
def detailUserEmail(request, user_email):
    try:
        user = User.objects.get(user_email=user_email)
    except User.DoesNotExist:
        raise Http404("User does not exist")
    user_diary_list = Diary.objects.all().filter(diary_user=user)
    user_diary_serialized = serializers.serialize('json', user_diary_list)
    return JsonResponse(user_diary_serialized, safe=False)

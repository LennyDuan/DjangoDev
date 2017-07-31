from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Feedback, Answer, Study
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to user api index page!")

# Get User List
#@csrf_exempt
def userAll(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_user_list = User.objects.all()
    all_user_serialized = serializers.serialize('json', all_user_list)
    return JsonResponse(all_user_serialized, safe=False)

# Get User by pk id
def userDetail(request, pk_id):
    user = get_object_or_404(User, pk=pk_id)
    user_serialized = serializers.serialize('json', [user])
    return JsonResponse(user_serialized, safe=False)

# Get User by user_email
def detailUserEmail(request, user_email):
    user = get_object_or_404(User, user_email=user_email)
    user_serialized = serializers.serialize('json', [user])
    return JsonResponse(user_serialized, safe=False)

# Get User's Study by pk id
def userStudy(request, pk_id):
    user = get_object_or_404(User, pk=pk_id)
    study = user.user_study
    study_serialized = serializers.serialize('json', [study])
    return JsonResponse(study_serialized, safe=False)

# Get User's Feedback by pk id
def userFeedback(request, pk_id):
    user = get_object_or_404(User, pk=pk_id)
    feedback = user.user_feedback
    feedback_serialized = serializers.serialize('json', [feedback])
    return JsonResponse(feedback_serialized, safe=False)

# Get User's Answers by pk id
def userAnswer(request, pk_id):
    user = get_object_or_404(User, pk=pk_id)
    feedback = user.user_feedback
    user_answer_list = Answer.objects.all().filter(answer_feedback=feedback)
    user_answer_serialized = serializers.serialize('json', user_answer_list)
    return JsonResponse(user_answer_serialized, safe=False)

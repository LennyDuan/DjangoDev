from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Feedback, Answer
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to user/feedback api index page!")

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

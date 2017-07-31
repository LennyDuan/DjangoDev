from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Feedback, Answer, Study
import json
#from django.views.decorators.csrf import csrf_exempt

# Create Diary API
def index(request):
    return HttpResponse("Hello, welcome to feedback api index page!")

# Get Feedback List
#@csrf_exempt
def feedbackAll(request):
    # Post one Diary
    if request.method == 'POST':
        #received_json_data = json.loads(request.body.decode("utf-8"))
        return HttpResponse("it was post request!!")

    all_feedback_list = Feedback.objects.all()
    all_feedback_serialized = serializers.serialize('json', all_feedback_list)
    return JsonResponse(all_feedback_serialized, safe=False)

# Get Feedback by pk id
def feedbackDetail(request, pk_id):
    feedback = get_object_or_404(Feedback, pk=pk_id)
    feedback_serialized = serializers.serialize('json', [feedback])
    return JsonResponse(feedback_serialized, safe=False)

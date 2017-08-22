from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Feedback, Answer, Study
from django.views.decorators.csrf import csrf_exempt
#from django.views.decorators.csrf import csrf_exempt

# Delete Feedback by id
@csrf_exempt
def feedbackDelete(request, feedback_id):
    feedback = get_object_or_404(Feedback, feedback_id=feedback_id)
    feedback.delete()
    return HttpResponse("This is the DELETE api!!")

from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import UserInfo, Feedback, Answer, Study
from django.views.decorators.csrf import csrf_exempt
import json

# POST Feedback
@csrf_exempt
def feedbackPost(request):
    if request.method == 'POST':
        try:
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            feedback_id = body["feedback_id"]
            feedback_state = body["feedback_state"]
            feedback = Feedback(
                feedback_id=feedback_id,
                feedback_state=feedback_state
            )
            # Feedback model Save
            feedback.save()
            return HttpResponse("it was a Feedback post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("This is the POST api!!")

# UPDATE one Feedback
@csrf_exempt
def feedbackPostUpdate(request, pk_id):
    if request.method == 'POST':
        try:
            feedback = get_object_or_404(Feedback, pk=pk_id)
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)
            feedback_state = body["feedback_state"]
            feedback.feedback_state = feedback_state
            # Feedback model update and save
            feedback.save()
            return HttpResponse("it was a update Feedback post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("This is the POST api!!")

# POST Answer
@csrf_exempt
def answerPost(request, pk_id):
    if request.method == 'POST':
        try:
            feedback = get_object_or_404(Feedback, pk=pk_id)
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)

            answer_id = body["answer_id"]
            answer_question = body["answer_question"]
            answer_before = body["answer_before"]
            answer_after = body["answer_after"]
            answer_feedback = feedback

            answer = Answer(
                answer_id=answer_id,
                answer_question=answer_question,
                answer_before=answer_before,
                answer_after=answer_after,
                answer_feedback=answer_feedback
            )
            # Answer model save
            answer.save()
            return HttpResponse("it was Answer post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("This is the POST api!!")

# POST Answer Update
@csrf_exempt
def answerPostUpdate(request, pk_id):
    if request.method == 'POST':
        try:
            answer = get_object_or_404(Answer, pk=pk_id)
            received_data = request.body.decode("utf-8")
            body = json.loads(received_data)

            answer_id = body["answer_id"]
            answer_question = body["answer_question"]
            answer_before = body["answer_before"]
            answer_after = body["answer_after"]

            answer.answer_id = answer_id
            answer.answer_question = answer_question
            answer.answer_before = answer_before
            answer.answer_after = answer_after
            # Answer model save
            answer.save()
            print (answer.answer_before)
            return HttpResponse("it was Answer Update post request!!__: " + json.dumps(body))
        except Exception as e:
            return HttpResponse("Post data failed: " + str(e))
    return HttpResponse("This is the POST api!!")

from django.shortcuts import get_object_or_404
from django.http import Http404, HttpResponse, JsonResponse
from django.core import serializers
from polls.models import User, Questionnaire, Question, Study
import json

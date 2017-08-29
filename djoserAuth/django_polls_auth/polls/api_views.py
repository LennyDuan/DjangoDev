from rest_framework import generics

from .models import Question, Choice

from django.contrib.auth.models import User
from .serializers import QuestionSerializer, ChoiceSerializer, UserSerializer

class QuestionList(generics.ListCreateAPIView):
    """
    Get / Create questions
    """
    queryset = Question.objects.all()
    serializer_class = QuestionSerializer

class QuestionDetail(generics.RetrieveDestroyAPIView):
    """
    Get / Delete questions
    """
    queryset = Question.objects.all()
    serializer_class = QuestionSerializer

class ChoiceDetail(generics.RetrieveUpdateAPIView):
    """
    Get / Update a Choice
    """
    queryset = Choice.objects.all()
    serializer_class = ChoiceSerializer

class UserCreate(generics.CreateAPIView):
    """
    Create a User
    """
    serializer_class = UserSerializer
    authentication_classes = ()
    permission_classes = ()

class UserDetail(generics.RetrieveAPIView):
    """
    Retrieve a User
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer

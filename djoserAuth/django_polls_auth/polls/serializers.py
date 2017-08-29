from rest_framework import serializers

from django.contrib.auth.models import User

from .models import Question, Choice

class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Choice

class QuestionSerializer(serializers.ModelSerializer):
    choices = ChoiceSerializer(many=True, read_only=True, required=False)

    class Meta:
        model = Question

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'email', 'password')
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        user = User(
            email = validated_data["email"],
            username = validated_data["username"]
        )
        user.set_password(validated_data["password"])
        user.save()
        return user

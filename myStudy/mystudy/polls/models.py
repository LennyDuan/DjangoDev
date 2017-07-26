from django.db import models
from django.utils.encoding import python_2_unicode_compatible
from django.utils import timezone
import datetime

# Create Study Database
class Study(models.Model):
    study_id = models.CharField(max_length=50, unique=True)
    study_field = models.CharField(max_length=50, blank=False)
    study_owner = models.CharField(max_length=50)
    study_start_date = models.DateTimeField('date published')
    # One study will have one Questionnaire
    study_questionnaire = models.OneToOneField(
        Questionnaire,
        on_delete=models.CASCADE,
    )

    def __str__(self):
        return "Study: %s - %s" % (self.study_field, self.study_owner)

class Questionnaire(models.Model):
    questionnaire_id = models.CharField(max_length=50, unique=True)
    questionnaire_text = models.CharField(max_length=200, blank=False)
    questionnaire_start_date = models.DateTimeField(auto_now_add=True, 'date published')

    def __str__(self):
        return self.questionnaire_text

class Question(models.Model):
    question_id = models.CharField(max_length=50, unique=True)
    question_text = models.CharField(max_length=200, blank=False)
    # A Questionnaire will have lots of Questions
    question_questionnaire = models.ForeignKey(Questionnaire, on_delete=models.CASCADE)

    def __str__(self):
        return self.question_text

# Create User Database
class User(models.Model):
    user_id = models.CharField(max_length=50, unique=True)
    user_email = models.EmailField(unique=True, blank=False)
    user_name = models.CharField(max_length=50)
    user_start_date = models.DateTimeField('date published')
    user_end_date = models.DateTimeField('date finished')
    # One User will have one Study and one Feedback
    user_study = models.OneToOneField(
        Study,
        on_delete=models.CASCADE,
    )

    user_feedback = models.OneToOneField(
        Feedback,
        on_delete=models.CASCADE,
    )
    def __str__(self):
        return "User: %s" % self.user_email

class Feedback(models.Model):
    feedback_id = models.CharField(max_length=50, unique=True)
    feedback_start_date = models.DateTimeField(auto_now_add=True, 'date published')
    feedback_end_date = models.DateTimeField(auto_now=True, 'date finished')

    STATE_IN_FEEDBACK_CHOICES = (
        ("INI", 'Initial State'),
        ("DIARY", 'Diary State'),
        ("FIN", 'Final State'),
    )
    feedback_state = models.CharField(
        choices=STATE_IN_FEEDBACK_CHOICES,
        default=INI,
    )

    def __str__(self):
        return self.feedback_status

class Answer(models.Model):
    answer_id = models.CharField(max_length=50, unique=True)
    answer_question = models.CharField(max_length=200)
    answer_before = models.CharField(max_length=200)
    answer_after = models.CharField(max_length=200)

    # A Feedback will have lots of Answer
    answer_feedback = models.ForeignKey(Feedback, on_delete=models.CASCADE)
    def __str__(self):
        return "Question: %s" % answer_question

# Create Diary Database
class Diary(models.Model):
    diary_id = models.CharField(max_length=50, unique=True)
    diary_date = models.DateTimeField(auto_now_add=True, 'date published')
    diary_skill = models.CharField(max_length=50, black=False)
    diary_title = models.CharField(max_length=200)
    diary_detail = models.CharField()
    diary_img = models.ImageField()
    # A User will have lots of Diary
    diary_user = models.ForeignKey(User, on_delete=models.CASCADE)
    def __str__(self):
        return "Diary: %s - %s" % (diary_title, diary_skill)

from django.db import models
from django.utils.encoding import python_2_unicode_compatible
from django.utils import timezone
import datetime
from django.db import transaction

# Create Study Database
class Questionnaire(models.Model):
    questionnaire_id = models.CharField(max_length=50, unique=True)
    questionnaire_text = models.CharField(max_length=200, blank=False)
    questionnaire_field = models.CharField(max_length=50, blank=False, unique=True)
    questionnaire_start_date = models.DateTimeField('date published', auto_now_add=True)

    def __str__(self):
        return "%s - %s" % (self.questionnaire_field, self.questionnaire_text)

class Study(models.Model):
    study_id = models.CharField(max_length=50, unique=True)
    study_field = models.CharField(max_length=50, blank=False, unique=True)
    study_owner = models.CharField(max_length=50)
    study_start_date = models.DateField('Study Date Published', default=datetime.datetime.now)
    study_end_date = models.DateField('Study End Date end')
    is_study_active = models.BooleanField('Active Study? - all the other study will set to false if active this study', default=False)
    # One study will have one Questionnaire
    study_questionnaire = models.OneToOneField(
        Questionnaire,
        on_delete=models.CASCADE,
    )

    @transaction.atomic
    def save(self, *args, **kwargs):
        if self.is_study_active:
            Study.objects.filter(
                is_study_active=True).update(is_study_active=False)
        super(Study, self).save(*args, **kwargs)

    def __str__(self):
        return "Study: %s - %s" % (self.study_field, self.study_owner)

class Question(models.Model):
    question_id = models.CharField(max_length=50, unique=True)
    question_text = models.CharField(max_length=200, blank=False)
    # A Questionnaire will have lots of Questions
    question_questionnaire = models.ForeignKey(Questionnaire, on_delete=models.CASCADE)

    def __str__(self):
        return self.question_text

# Create User Database
class Feedback(models.Model):
    feedback_id = models.CharField(max_length=50, unique=True)
    feedback_start_date = models.DateField('date published', auto_now_add=True)
    feedback_end_date = models.DateField('date published', auto_now=True)

    STATE_IN_FEEDBACK_CHOICES = (
        ("INI", 'Initial State'),
        ("DIARY", 'Diary State'),
        ("FIN", 'Final State'),
    )
    feedback_state = models.CharField(
        choices=STATE_IN_FEEDBACK_CHOICES,
        default="INI",
        max_length=50,
    )

    def __str__(self):
        return "%s" % self.feedback_id

class UserInfo(models.Model):
    userInfo_id = models.CharField(max_length=50, unique=True)
    userInfo_age = models.CharField(max_length=50,default="10")
    userInfo_gender = models.CharField(max_length=50,default="Male")
    userInfo_start_date = models.DateField('date published')
    userInfo_end_date = models.DateField('date finished')
    # One User will have one Study and one Feedback
    userInfo_study = models.ForeignKey(
        Study,
        on_delete=models.CASCADE,
    )

    userInfo_feedback = models.OneToOneField(
        Feedback,
        on_delete=models.CASCADE,
    )
    def __str__(self):
        return "User: %s" % self.userInfo_id

class Answer(models.Model):
    answer_id = models.CharField(max_length=50, unique=True)
    answer_question = models.CharField(max_length=200)
    answer_before = models.CharField(max_length=200)
    answer_after = models.CharField(max_length=200)

    # A Feedback will have lots of Answer
    answer_feedback = models.ForeignKey(Feedback, on_delete=models.CASCADE)
    def __str__(self):
        return "Question: %s" % self.answer_question

# Create Diary Database
class Diary(models.Model):
    diary_id = models.CharField(max_length=50, unique=True)
    diary_date = models.DateTimeField('date published', auto_now_add=True)
    diary_skill = models.CharField(max_length=50, blank=False)
    diary_title = models.CharField(max_length=200, blank=False)
    diary_detail = models.CharField(max_length=1000)
    diary_latitude = models.CharField(max_length=30, blank=True)
    diary_longitude = models.CharField(max_length=30, blank=True)
    # diary_img = models.ImageField()
    # A User will have lots of Diary
    diary_userInfo = models.ForeignKey(UserInfo, on_delete=models.CASCADE)
    def __str__(self):
        return "Diary: %s - %s" % (self.diary_title, self.diary_skill)

# Create Skill Database
class Skill(models.Model):
    skill_id = models.CharField(max_length=50, unique=True)
    skill_name = models.CharField(max_length=50, unique=True)
    def __str__(self):
        return "%s" % self.skill_name

from django.test import TestCase
from .models import *
import datetime

# Create your tests here.
class QuestionnaireModelTests(TestCase):
    def test_questionnaire_model_can_be_created(self):
        questionnaire = Questionnaire(
            questionnaire_id="1",
            questionnaire_text="test text",
            questionnaire_field="test"
            )
        questionnaire.save()
        actual_data = Questionnaire.objects.get(questionnaire_id="1")
        self.assertEqual(actual_data.questionnaire_field, "test")

class StudyModelTests(TestCase):
    def test_study_model_can_be_created(self):
        questionnaire = Questionnaire(
            questionnaire_id="1",
            questionnaire_text="test text",
            questionnaire_field="test"
            )
        questionnaire.save()
        study = Study(
            study_id="1",
            study_field='test field',
            study_owner='test owner',
            study_questionnaire = questionnaire
            )
        study.save()
        actual_data = Study.objects.get(study_id="1")
        self.assertEqual(actual_data.study_field, "test field")

class QuestionModelTests(TestCase):
    def test_question_model_can_be_created(self):
        questionnaire = Questionnaire(
            questionnaire_id="1",
            questionnaire_text="test text",
            questionnaire_field="test"
            )
        questionnaire.save()

        question = Question(
            question_id="1",
            question_text="test text",
            question_questionnaire=questionnaire
            )
        question.save()
        actual_data = Question.objects.get(question_id="1")
        self.assertEqual(actual_data.question_text, "test text")

class FeedbackModelTests(TestCase):
    def test_feedback_model_can_be_created(self):
        feedback = Feedback(
            feedback_id="1",
            feedback_state="Initial State"
            )
        feedback.save()
        actual_data = Feedback.objects.get(feedback_id="1")
        self.assertEqual(actual_data.feedback_state, "Initial State")

class AnswerModelTests(TestCase):
    def test_answer_model_can_be_created(self):
        feedback = Feedback(
            feedback_id="1",
            feedback_state="Initial State"
            )
        feedback.save()

        answer = Answer(
            answer_id="1",
            answer_question="test question",
            answer_before="test answer before",
            answer_after="test answer after",
            answer_feedback = feedback
            )
        answer.save()
        actual_data = Answer.objects.get(answer_id="1")
        self.assertEqual(actual_data.answer_question, "test question")

class SkillModelTests(TestCase):
    def test_skill_model_can_be_created(self):
        skill = Skill(
            skill_id="1",
            skill_name='test skill'
            )
        skill.save()
        actual_data = Skill.objects.get(skill_id="1")
        self.assertEqual(actual_data.skill_name, "test skill")

class UserInfoModelTests(TestCase):
    def test_user_info_and_diary_model_can_be_created(self):
        feedback = Feedback(
            feedback_id="1",
            feedback_state="Initial State"
            )
        feedback.save()
        questionnaire = Questionnaire(
            questionnaire_id="1",
            questionnaire_text="test text",
            questionnaire_field="test"
            )
        questionnaire.save()
        study = Study(
            study_id="1",
            study_field='test field',
            study_owner='test owner',
            study_questionnaire = questionnaire
            )
        study.save()
        date = datetime.datetime.now()
        user = UserInfo(
            userInfo_id="1",
            userInfo_email="test email",
            userInfo_name="test name",
            userInfo_start_date=date,
            userInfo_end_date=date,
            userInfo_study=study,
            userInfo_feedback=feedback
        )
        user.save()
        actual_data = UserInfo.objects.get(userInfo_id="1")
        self.assertEqual(actual_data.userInfo_name, "test name")

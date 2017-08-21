from django.contrib import admin
from .models import *

class AnswerInline(admin.TabularInline):
    model = Answer
    extra = 0
    show_change_link = True

class DiaryInline(admin.TabularInline):
    model = Diary
    extra = 0
    show_change_link = True

class QuestionInline(admin.StackedInline):
    model = Question
    extra = 0
    show_change_link = True

class FeedbackAdmin(admin.ModelAdmin):
    inlines = [
        AnswerInline,
    ]
    search_fields = ['feedback_state']
    list_display = ('feedback_id', 'feedback_state', 'feedback_start_date')

class QuestionnaireAdmin(admin.ModelAdmin):
    inlines = [QuestionInline]
    list_filter = ['questionnaire_start_date']
    search_fields = ['questionnaire_field']
    list_display = ('questionnaire_field', 'questionnaire_text', 'questionnaire_start_date')

class StudyAdmin(admin.ModelAdmin):
    list_display = ('study_field', 'study_owner', 'study_questionnaire')
    list_filter = ['study_start_date']
    search_fields = ['study_field']

class UserInfoAdmin(admin.ModelAdmin):
    list_display = ('userInfo_email', 'userInfo_study', 'userInfo_feedback')
    list_filter = ['userInfo_study']
    search_fields = ['userInfo_email']
    inlines = [DiaryInline]

class DiaryAdmin(admin.ModelAdmin):
    list_display = ('diary_date', 'diary_userInfo', 'diary_title', 'diary_skill')
    list_filter = ['diary_date']
    search_fields = ['diary_id']

class AnswerAdmin(admin.ModelAdmin):
    list_display = ('answer_question', 'answer_feedback')
    list_filter = ['answer_feedback']
    search_fields = ['answer_question']

class QuestionAdmin(admin.ModelAdmin):
    list_display = ('question_text', 'question_questionnaire')
    list_filter = ['question_questionnaire']
    search_fields = ['question_text']

# Register your models here.
admin.site.register(Questionnaire, QuestionnaireAdmin)
admin.site.register(Study, StudyAdmin)
admin.site.register(Question, QuestionAdmin)

admin.site.register(Feedback, FeedbackAdmin)
admin.site.register(Answer, AnswerAdmin)

admin.site.register(UserInfo, UserInfoAdmin)
admin.site.register(Diary, DiaryAdmin)
admin.site.register(Skill)

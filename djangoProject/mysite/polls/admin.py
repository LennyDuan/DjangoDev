from django.contrib import admin
from .models import Question
from .models import Choice

class ChoiceInline(admin.StackedInline):
    model = Choice
    extra = 3


class QuestionAdmin(admin.ModelAdmin):
    fieldsets = [
        (None,               {'fields': ['question_text']}),
        ('Date information', {'fields': ['pub_time'], 'classes': ['collapse']}),
    ]
    inlines = [ChoiceInline]
    list_display = ('question_text', 'pub_time', 'was_published_recently')
    list_filter = ['pub_time']
    search_fields = ['question_text']
      
admin.site.register(Question, QuestionAdmin)
admin.site.register(Choice)

# Register your models here.

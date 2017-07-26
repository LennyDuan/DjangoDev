from django.shortcuts import render, get_object_or_404
from django.urls import reverse
from django.views import generic
from django.http import Http404, HttpResponse, HttpResponseRedirect

# Create your views here.
def index(request):
    # return render(request, 'polls/index.html')
    return HttpResponse("Hello, welcome to MyStudy website!!")

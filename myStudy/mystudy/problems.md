#### 1:
##### Problem:
In order to allow non-dict objects to be serialized set the safe parameter to False.
##### Solve:
from django.core import serializers
return JsonResponse(all_diary_serialized, safe=False)

#### 2:
##### Problem:
Django CSRF Cookie Not Set
##### Solve:
from django.views.decorators.csrf import csrf_exempt
csrf_exempt

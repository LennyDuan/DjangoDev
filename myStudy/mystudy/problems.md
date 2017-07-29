#### 1:
##### Problem:
In order to allow non-dict objects to be serialized set the safe parameter to False.
##### Solve:
from django.core import serializers
return JsonResponse(all_diary_serialized, safe=False)

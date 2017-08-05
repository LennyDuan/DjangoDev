#### 1:
##### Problem:
In order to allow non-dict objects to be serialized set the safe parameter to False.
##### Solve:
from django.core import serializers
return JsonResponse(all_diary_serialized, safe=False)

#### 2:
##### Problem:
Django CSRF Cookie Not Set,POST
##### Solve:
from django.views.decorators.csrf import csrf_exempt
csrf_exempt

#### 3:
##### Problem:
When I use Postman to test POST api:
received_json_data = json.loads(request.body.decode("utf-8"))
json.decoder.JSONDecodeError: Expecting value: line 1 column 1 (char 0)
##### Solve:
from django.views.decorators.csrf import csrf_exempt
add \@csrf_exempt
code right:
received_data = request.body.decode("utf-8")
body = json.loads(received_data)

But need to choose 'raw' and 'json' in body not the form-data

#### 4:
Need to modify data model UserInfo -> User in order for the future authorisation.

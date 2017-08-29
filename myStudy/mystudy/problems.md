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

#### 5:
Unique Android Id identifier
https://android-developers.googleblog.com/2011/03/identifying-app-installations.html
Not Solve well:

#### 6:
Lack of contraints
infer constraints

#### 7:
Connect to localhost from AVD
https://developer.android.com/studio/run/emulator-networking.html
10.0.2.2	Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)
Server side:
DEBUG = True
ALLOWED_HOSTS = ['\*']

#### 8:
Valid Json from GET API
http://jsonviewer.stack.hu/#http://127.0.0.1:8000/polls/api/v1/study/

#### 9:
Data model is wrong. UserInfo to Study should be many to one. But it is one to one currently. Need to modify data model

#### 10:
RecycleView can't to click listen....User List View

#### 11:
Get location is always null... Need to use google map on virtual device to store last know location

#### User auth
How to auth user/admin will be an unsolved problem. It should use Token now session. JWT or djoser will be a good example and suitble usage. 

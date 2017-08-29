var pollsApp = angular.module('pollsApp', [
  'ngRoute',
  'ngCookies'
])

pollsApp.factory('tokenInterceptor', function($cookies) {
  var headerName = "Authorization";
  var cookieName = "token";

  return {
    request: function(config) {
      config.headers = config.headers || {};
      console.log($cookies.get('token'));
      if ($cookies.get('token')) {
        config.headers["Authorization"] = 'Token ' + $cookies.get('token');
      }
      return config;
    },
    responseError: function (respones) {
      location.href=login_url;
    }
  }
});

pollsApp.config(function ($httpProvider) {
  $httpProvider.defaults.xsrfCookieName = 'csrftoken';
  $httpProvider.defaults.xsrfHeaderName = 'X-CSRFToken';
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
  $httpProvider.interceptors.push('tokenInterceptor');
});

pollsApp.controller('PollsController',
  function PollsController($scope, $http) {
    $scope.polls = null;

    $http({
      method: 'GET',
      url: '/polls/api/questions',
    }).then(function (response) {
      $scope.polls = response.data;
    });
});

pollsApp.controller('QuestionController',
  function QuestionController($scope, $http, $routeParams, $location) {
    $scope.question_id = $routeParams.question_id;
    $scope.question = null;
    $scope.selected_choice = {
      choice: 0
    };

    $http({
      method: 'GET',
      url: '/polls/api/questions/'+$scope.question_id
    }).then(function (response) {
      $scope.question = response.data;
    })

    $scope.addVote = function () {
      $http({
        method: 'POST',
        url: '/polls/'+$scope.question_id+'/vote/',
        data: "choice="+$scope.selected_choice.choice
      }).then(function (response) {
        $location.url('/polls/'+$scope.question_id+'/results');
      })
    }
  })

angular.
  module('pollsApp').
  config(['$routeProvider',
    function config($routeProvider) {

      $routeProvider.
        when('/', {
          templateUrl: '/static/templates/index.html',
          controller: 'PollsController'
        }).
        when('/polls/:question_id/results', {
          templateUrl: '/static/templates/results.html',
          controller: 'QuestionController'
        }).
        when('/polls/:question_id', {
          templateUrl: '/static/templates/detail.html',
          controller: 'QuestionController'
        }).
        otherwise('/');
    }
  ]);

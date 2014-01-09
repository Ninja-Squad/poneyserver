'use strict';

angular.module('services', []);
angular.module('constants', []);
angular.module('controllers', []);
angular.module('directives', []);

angular.module('poney2App', [
  'ngRoute',
  'ngCookies',
  'services',
  'constants',
  'controllers',
  'directives'
])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/register', {
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .when('/races', {
        templateUrl: 'views/races.html',
        controller: 'RacesCtrl'
      })
      .when('/races/:raceId', {
        templateUrl: 'views/race.html',
        controller: 'RaceCtrl'
      })
      .when('/races/:raceId/live', {
        templateUrl: 'views/live.html',
        controller: 'LiveCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .run(function(AuthenticationService){
    AuthenticationService.init();
  });

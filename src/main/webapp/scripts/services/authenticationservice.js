'use strict';

angular.module('services')
  .service('AuthenticationService', function AuthenticationService($http, $cookies, $location, server) {

    var login = function(credentials){
      $http.post(server + 'authentication', credentials)
        .success(function(token){
          $cookies.customAuth = token;
          $http.defaults.headers.common['Custom-Authentication'] = $cookies.customAuth;
          $location.path('/');
        });
    };

    var init = function(){
      $http.defaults.headers.common['Custom-Authentication'] = $cookies.customAuth;
    };

    var isLogged = function(){
      return $cookies.customAuth !== undefined;
    };

    var logout = function(){
      delete $cookies.customAuth;
    };

    return {
      login: login,
      init: init,
      isLogged: isLogged,
      logout: logout
    };
  });
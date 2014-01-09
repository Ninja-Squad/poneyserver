'use strict';

angular.module('services')
  .service('UsersService', function UsersService($http, $location, server) {

    var usersUrl = server + 'users';
  
    var list = function(success){
      $http.get(usersUrl).success(function(data){
        success(data);
      });
    };

    var register = function(user){
      $http.post(usersUrl, user).success(function(){
        $location.path('/');
      });
    };

    return {
      list: list,
      register: register
    };
  });

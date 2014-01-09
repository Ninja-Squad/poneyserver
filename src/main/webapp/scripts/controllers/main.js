'use strict';

angular.module('controllers')
  .controller('MainCtrl', function ($scope, UsersService, AuthenticationService) {

    $scope.list = function(){
      UsersService.list(function(data){
        $scope.users = data;
      });
    };

    $scope.isLogged = function(){
      return AuthenticationService.isLogged();
    };

    $scope.logout = function(){
      AuthenticationService.logout();
    };

    // we initialize the scope with the users when the controller is created
    $scope.list();
  });

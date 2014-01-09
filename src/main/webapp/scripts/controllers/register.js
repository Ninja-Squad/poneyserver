'use strict';

angular.module('controllers')
  .controller('RegisterCtrl', function ($scope, UsersService) {
    
    $scope.register = function(user){
      UsersService.register(user);
    };

  });

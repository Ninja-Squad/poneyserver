'use strict';

angular.module('controllers')
  .controller('LoginCtrl', function ($scope, AuthenticationService) {

    $scope.login = function(credentials){
      AuthenticationService.login(credentials);
    };

  });

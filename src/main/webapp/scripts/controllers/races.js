'use strict';

angular.module('controllers')
  .controller('RacesCtrl', function ($scope, RacesService) {

    $scope.init = function(){
      RacesService.list(function(data){
        $scope.races = data;
      });
    };

    // init the races
    $scope.init();
  });

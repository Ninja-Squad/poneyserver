'use strict';

angular.module('controllers')
  .controller('RaceCtrl', function ($scope, $routeParams, $location, RacesService) {

    var raceId = $routeParams.raceId;
    
    $scope.init = function(){
      RacesService.show(raceId, function(data){
        $scope.race = data;
      });
    };

    $scope.bet = function(poney){
      RacesService.bet(raceId, poney, $scope.init);
    };

    $scope.cancelBet = function(){
      RacesService.cancelBet(raceId, $scope.init);
    };

    $scope.start = function(){
      RacesService.start(raceId, function(){
        $location.path('/races/' + raceId + '/live');
      });
    };

    $scope.init();

  });

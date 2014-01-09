'use strict';

angular.module('services')
  .service('RacesService', function RacesService($http, $q, server) {
    
    var list = function(success){
      $http.get(server + 'races').success(function(data){
        success(data);
      });
    };

    var show = function(raceId, success){
      $http.get(server + 'races/' + raceId).success(function(data){
        success(data);
      });
    };

    var bet = function(raceId, poney, success){
      // cancel bet then post new bet
      $http.delete(server + 'bets/' + raceId).then(function(){
        $http.post(server + 'bets/', { raceId: raceId, poney: poney})
        .then(function(data){
          success(data);
        });
      });
    };

    var cancelBet = function(raceId, success){
      // cancel bet
      $http.delete(server + 'bets/' + raceId).success(function(){
        success();
      });
    };

    var start = function(raceId, success){
      $http.post(server + 'running', raceId).success(function(){
        success();
      });
    };

    return {
      list: list,
      show: show,
      bet: bet,
      cancelBet: cancelBet,
      start: start
    };

  });

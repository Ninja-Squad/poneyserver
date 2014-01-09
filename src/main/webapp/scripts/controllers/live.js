'use strict';

angular.module('controllers')
  .controller('LiveCtrl', function ($scope, $routeParams, server, RacesService) {
    var raceId = $routeParams.raceId;

    $scope.init = function(){
      // connect to race positions
      var socket = new SockJS(server + 'race');
      var stompClient = Stomp.over(socket);

      stompClient.connect('ced', 'password', function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + raceId, function(message) {
          $scope.positions = angular.fromJson(message.body).positions;
          $scope.$apply();
        });
      });

      RacesService.show(raceId, function(data){
        $scope.race = data;
      })
    
    };



    $scope.init();
  });

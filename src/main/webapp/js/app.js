angular.module('test', []).controller('RaceCtrl', function($scope, $http) {
    $scope.startRace = function() {
        $http.post('running', 1);
    };

    var socket = new SockJS("/poneyserver/race");
    var stompClient = Stomp.over(socket);

    stompClient.connect('jb', 'password', function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/1', function(message) {
            $scope.message = angular.fromJson(message.body);
            $scope.$apply();
        });
    });
});

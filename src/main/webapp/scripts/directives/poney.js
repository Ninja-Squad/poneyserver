'use strict';

angular.module('directives')
  .directive('poney', function () {
    return {
      template: '<div><img ng-class="{greenBorder: betted === \'true\'}" ng-src="images/poney-{{ name | lowercase }}.png" alt="{{ name }}"></img></div>',
      restrict: 'E',
      scope: {
        name: '@',
        betted: '@'
      }
    };
  });

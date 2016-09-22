app.controller('LoginController', ['$scope', '$location', function ($scope, $location) {

    'use strict';

    $scope.user = {};

    $scope.validar = function () {
        if ($scope.user.name == "usuario" && $scope.user.password == "1234") {
            $location.url('/terminal');
        }
    };

}]);
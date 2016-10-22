app.controller('LoginController', ['$scope', '$http', '$location', 'LoadingBackdrop', 'toaster', function ($scope, $http, $location, LoadingBackdrop, toaster) {

    'use strict';

    $scope.user = {};

    $scope.validar = function () {
        if ($scope.user.name !== "" && $scope.user.password !== "") {
            var promise = $http.post('/DDS2016/login?name=' + $scope.user.name + '&password=' + $scope.user.password);
            LoadingBackdrop.show();
            promise.then(function (response) {
                LoadingBackdrop.hide();
                if (response.data.tipo.toLowerCase() === "terminal") {
                    $location.url('/terminal');
                }
            }, function (response) {
                toaster.error(response.data.error);
                LoadingBackdrop.hide();
            });
        }
    };

}]);
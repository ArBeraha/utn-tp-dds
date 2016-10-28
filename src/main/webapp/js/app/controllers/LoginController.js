app.controller('LoginController', ['$scope', '$http', '$location', 'LoadingBackdrop', 'toaster', '$cookies', function ($scope, $http, $location, LoadingBackdrop, toaster, $cookies) {

    'use strict';

    $scope.user = {};

    var usuarioLogueado = $cookies.getObject('user');

    var init = function () {
        if (usuarioLogueado !== undefined) {
            if (usuarioLogueado.tipo.toLowerCase() === "administrador") {
                $location.url('admin');
            } else {
                $location.url('/terminal');
            }
        }
    };

    $scope.validar = function () {
        if ($scope.user.name !== "" && $scope.user.password !== "") {
            var promise = $http.post('/DDS2016/login?name=' + $scope.user.name + '&password=' + $scope.user.password);
            LoadingBackdrop.show();
            promise.then(function (response) {
                var user = JSON.parse(response.data);
                LoadingBackdrop.hide();
                $cookies.putObject('user', user);
                if (user.tipo.toLowerCase() === "administrador") {
                    $location.url('/admin');
                } else {
                    $location.url('/terminal');
                }
            }, function (response) {
                toaster.error(response.data.error);
                LoadingBackdrop.hide();
            });
        }
    };

    init();

}]);
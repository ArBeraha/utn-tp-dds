app.controller('MainController', ['$scope', '$location', '$rootScope', '$cookies', 'BuscarPOIService', 'toaster', function ($scope, $location, $rootScope, $cookies, BuscarPOIService, toaster) {
    'use strict';

    $scope.showLoadingBackdrop = false;

    $scope.usuarioLogueado = false;

    $rootScope.$on("showLoadingBackdrop", function (event, state) {
        $scope.showLoadingBackdrop = state;
    });

    $rootScope.$on('$routeChangeSuccess', function () {
        $scope.usuarioLogueado = $location.path() !== "" && $location.path() !== "/";
    });

    $scope.cerrarSesion = function () {
        $cookies.remove('user');
        $location.url('/');
    };

}]);
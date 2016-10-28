app.controller('AdminController', ['$scope', '$cookies', '$location', function ($scope, $cookies, $location) {

    'use strict';

    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

    $scope.configurarAcciones = function () {
        $location.url('/admin/acciones');
    };

}]);
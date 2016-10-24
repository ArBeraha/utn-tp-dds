app.controller('AdminController', ['$scope', '$cookies', function ($scope, $cookies) {

    'use strict';

    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

    $scope.configurarAcciones = function () {
        $location.url('/admin/acciones');
    };

}]);
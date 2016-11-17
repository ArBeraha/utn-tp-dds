app.controller('HistorialBusquedasController', ['$scope', '$cookies', function ($scope, $cookies) {

    'use strict';

    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

}]);
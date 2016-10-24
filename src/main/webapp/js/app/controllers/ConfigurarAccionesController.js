app.controller('ConfigurarAccionesController', ['$scope', '$http', '$cookies', 'Acciones', function ($scope, $http, $cookies, Acciones) {

    'use strict';

    $scope.acciones = [];
    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");
    var getAccionesPromise = Acciones.lista(usuario.id);

    var init = function () {

    };

    init();

}]);
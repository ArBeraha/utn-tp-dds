app.controller('ConfigurarAccionesController', ['$scope', '$http', '$cookies', '$location', 'Acciones', 'LoadingBackdrop', 'toaster', function ($scope, $http, $cookies, $location, Acciones, LoadingBackdrop, toaster) {

    'use strict';

    LoadingBackdrop.show();
    $scope.acciones = [];
    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");
    var getAccionesPromise = Acciones.lista();

    var init = function () {
        getAccionesPromise.then(function (response) {
            $scope.acciones = response.data;
            LoadingBackdrop.hide();
        }, function () {
            LoadingBackdrop.hide();
        });
    };

    $scope.volver = function () {
        $location.url('/admin');
    };

    $scope.enviarAcciones = function () {
        var enviarAccionesPromise = Acciones.enviar($scope.acciones);
        enviarAccionesPromise.then(function (response) {
            toaster.success("Acciones guardadas con éxito!");
        }, function (response) {
            toaster.error("Hubo un problema al guardar las acciones");
        });
    };

    init();

}]);
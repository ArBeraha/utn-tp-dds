app.controller('ConfigurarAccionesController', ['$scope', '$http', '$cookies', '$location', 'Acciones', 'LoadingBackdrop', 'toaster', function ($scope, $http, $cookies, $location, Acciones, LoadingBackdrop, toaster) {

    'use strict';

    LoadingBackdrop.show();
    $scope.acciones = [];
    $scope.terminales = [];
    $scope.hayTerminalSeleccionada = false;
    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

    var getTerminalesPromise = Acciones.terminales();

    var init = function () {
        getTerminalesPromise.then(function (response) {
            $scope.terminales = response.data;
            LoadingBackdrop.hide();
        }, function () {
            LoadingBackdrop.hide();
        });
    };

    $scope.volver = function () {
        $location.url('/admin');
    };

    $scope.enviarAcciones = function (terminalSeleccionada) {
        var terminalId = getTerminalId(terminalSeleccionada);
        var enviarAccionesPromise = Acciones.enviar(terminalId, $scope.acciones);
        enviarAccionesPromise.then(function (response) {
            toaster.success("Acciones guardadas con éxito!");
        }, function (response) {
            toaster.error("Hubo un problema al guardar las acciones");
        });
    };

    $scope.terminalSelectChange = function (terminalSeleccionada) {
        $scope.hayTerminalSeleccionada = true;
        var terminalId = getTerminalId(terminalSeleccionada);
        var getAccionesPromise = Acciones.lista(terminalId);
        getAccionesPromise.then(function (response) {
            $scope.acciones = response.data;
            LoadingBackdrop.hide();
        }, function () {
            LoadingBackdrop.hide();
        });
    };

    var getTerminalId = function (nombreTerminal) {
        for (var i = 0; i < $scope.terminales.length; i++) {
            if ($scope.terminales[i].username === nombreTerminal) {
                return $scope.terminales[i].id;
            }
        }
    };

    init();

}]);
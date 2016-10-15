app.controller('BuscadorController', ['$scope', 'toaster', 'BuscarPOIService', 'LoadingBackdrop', function ($scope, toaster, BuscarPOIService, LoadingBackdrop) {

    'use strict';

    $scope.sinResultados = false;
    $scope.pois = [];

    $scope.buscarPOI = function (texto) {
        if (texto !== undefined && texto !== '') {
            texto.trim();
            var promise = BuscarPOIService.buscarPOI(texto);
            LoadingBackdrop.show();
            promise.then(function (response) {
                $scope.pois = response.data;
                $scope.sinResultados = ($scope.pois.length === 0);
                LoadingBackdrop.hide();
            }, function () {
                toaster.pop('error', "Error", "Hubo un problema al realizar la búsqueda. Por favor intente de nuevo más tarde");
                LoadingBackdrop.hide();
            });
        }
    };

    $scope.verInfo = function (poi) {
        //alert("Se envía el id: " + poi.id + " al backend");
        var esCercanopromise = BuscarPOIService.esCercano(poi.id);
        var estaDisponiblepromise = BuscarPOIService.estaDisponible(poi.id);
        var esCercano;
        var estaDisponible;
        esCercanopromise.then(function (response) {
            esCercano = response.data;
            return estaDisponiblepromise;
        }, function () {
            toaster.pop('error', "Error", "Hubo un problema al realizar la consulta. Por favor intente de nuevo más tarde");
        }).then(function (response) {
            estaDisponible = response.data;
            var mensajeCercano = "no está cerca";
            var mensajeDisponible = "no está disponible";
            if (esCercano) {
                mensajeCercano = "está cerca";
            }
            if (estaDisponible) {
                mensajeDisponible = "está disponible";
            }
            alert("El punto de interés " + poi.nombre + " " + mensajeCercano + " y " + mensajeDisponible);
        }, function () {
            toaster.pop('error', "Error", "Hubo un problema al realizar la consulta. Por favor intente de nuevo más tarde");
        });
    };

}]);
app.controller('MainController', ['$scope', "BuscarPOIService", function ($scope, BuscarPOIService) {
    'use strict';

    $scope.buscarPOI = function (texto) {
        BuscarPOIService.buscarPOI(texto);
    };

}]);
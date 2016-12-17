app.factory('BuscarPOIService', ['$http', function ($http) {
    'use strict';

    return {
        buscarPOI: function (id, texto) {
            return $http.get('/DDS2016/pois/' + id + '/' + texto);
        },
        esCercano: function (id, poiId) {
            return $http.get('/DDS2016/poi/' + poiId + '/' + id + '/cercano');
        },
        estaDisponible: function (poiId) {
            return $http.get('/DDS2016/poi/' + poiId + '/disponible');
        }
    };

}]);
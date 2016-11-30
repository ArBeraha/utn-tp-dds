app.factory('BuscarPOIService', ['$http', function ($http) {
    'use strict';

    return {
        buscarPOI: function (id, texto) {
            return $http.get('/DDS2016/pois/' + id + '/' + texto);
        },
        esCercano: function (poiId) {
            return $http.get('/DDS2016/poi/' + poiId + '/1/cercano');
        },
        estaDisponible: function (poiId) {
            return $http.get('/DDS2016/poi/' + poiId + '/disponible');
        }
    };

}]);
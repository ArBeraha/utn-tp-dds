app.factory('Acciones', ['$http', function ($http) {
    'use strict';

    return {
        lista: function (userId) {
            return $http.get('/DDS2016/accion/' + userId);
        },
        ejecutar: function (userId, accionId) {
            return $http.get('/DDS2016/accion/' + userId + '/' + accionId);
        },
        ejecutarCon: function (userId, accionId, params) {
            return $http.get('/DDS2016/accion/' + userId + '/' + accionId + '/' + params);
        },
        deshacer: function (userId) {
            return $http.get('/DDS2016/undo/' + userId);
        }
    };

}]);
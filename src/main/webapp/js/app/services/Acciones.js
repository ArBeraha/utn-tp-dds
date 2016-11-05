app.factory('Acciones', ['$http', function ($http) {
    'use strict';

    return {
        lista: function () {
            return $http.get('/DDS2016/accionesBusqueda/1');
        },
        enviar: function (acciones) {
            return $http.post('/DDS2016/accionesBusqueda/1', acciones);
        }
    };

}]);
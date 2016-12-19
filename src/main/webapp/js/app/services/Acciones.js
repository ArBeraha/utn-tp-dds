app.factory('Acciones', ['$http', function ($http) {
    'use strict';

    return {
        lista: function (id) {
            return $http.get('/DDS2016/accionesBusqueda/' + id);
        },
        enviar: function (id, acciones) {
            return $http.post('/DDS2016/accionesBusqueda/' + id, acciones);
        },
        terminales: function () {
            return $http.get('/DDS2016/terminales');
        }
    };

}]);
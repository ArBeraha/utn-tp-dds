app.factory('BuscarPOIService', function () {
    'use strict';

    return {
        buscarPOI: function (texto) {
            if (texto !== undefined && texto !== '') {
                texto.trim();
                console.log(texto);
            }
        }
    };

});
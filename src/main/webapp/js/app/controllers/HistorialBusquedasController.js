app.controller('HistorialBusquedasController', ['$scope', '$cookies', '$http', 'toaster', 'LoadingBackdrop', function ($scope, $cookies, $http, toaster, LoadingBackdrop) {

    'use strict';

    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

    $scope.showModal = false;
    $scope.modalHeader = "Detalle POIs";
    $scope.poiNames = [];
    $scope.nombreUsuario = {};
    $scope.fecha = {};

    $scope.noHayResultados = false;

    $scope.fechaHastaMax = new Date();
    $scope.fechaDesdeMax = new Date();

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.desde = new Date(year, month, day);
    };

    $scope.format = 'dd-MMMM-yyyy';

    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

    function getDayClass(data) {
        var date = data.date,
            mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }

    $scope.validarFechaDesde = function () {
        if ($scope.fecha.hasta !== null && $scope.fecha.desde > $scope.fecha.hasta) {
            $scope.fecha.desde = null;
            toaster.error('La fecha inicial no puede ser mayor a la fecha final');
        }
    };

    $scope.validarFechaHasta = function () {
        if ($scope.fecha.desde !== null && $scope.fecha.hasta < $scope.fecha.desde) {
            $scope.fecha.hasta = null;
            toaster.error('La fecha final no puede ser menor a la fecha inicial');
        }
    };

    $scope.submit = function () {
        var fechaDesde;
        var fechaHasta;
        fechaDesde = ($scope.fecha.desde === null || $scope.fecha.desde === undefined) ? 0 : new Date($scope.fecha.desde.setHours(0, 0, 0, 0)).getTime();
        fechaHasta = ($scope.fecha.hasta === null || $scope.fecha.hasta === undefined) ? 0 : new Date($scope.fecha.hasta.setHours(23, 59, 59, 99)).getTime();

        if ((fechaDesde !== 0 && fechaDesde !== undefined) || (fechaHasta !== 0 && fechaHasta !== undefined) || ($scope.nombreUsuario.value !== "" && $scope.nombreUsuario.value !== undefined)) {
            var promise = $http.get('/DDS2016/historial/' + fechaDesde + '/' + fechaHasta + '/' + nombreUsuario.value);
            LoadingBackdrop.show();
            promise.then(function (response) {
                $scope.resultados = response.data;
                if ($scope.resultados.length === 0) {
                    $scope.noHayResultados = true;
                } else {
                    $scope.noHayResultados = false;
                }
                LoadingBackdrop.hide();
            }, function (response) {
                toaster.error('Hubo un problema al obtener el historial');
                LoadingBackdrop.hide();
            });
        } else {
            toaster.error("Complete alguno de los campos!");
        }
    };

    $scope.mostrarListaPois = function (resultado) {
        var resultados = resultado.resultados;
        for (var i = 0; i < resultados.length; i++) {
            $scope.poiNames.push(resultados[i].nombre);
        }
        $scope.showModal = true;
    };

    $scope.cerrarModal = function () {
        $scope.showModal = false;
        $scope.poiNames = [];
    };

}]);
app.controller('HistorialBusquedasController', ['$scope', '$cookies', 'toaster', function ($scope, $cookies, toaster) {

    'use strict';

    var usuario = $cookies.getObject('user');
    $scope.isAdmin = (usuario !== undefined && usuario.tipo.toLowerCase() === "administrador");

    $scope.fecha = {};

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
        if ($scope.fecha.desde > $scope.fecha.hasta) {
            $scope.fecha.desde = null;
            toaster.error('La fecha inicial no puede ser mayor a la fecha final');
        }
    };

    $scope.validarFechaHasta = function () {
        if ($scope.fecha.hasta < $scope.fecha.desde) {
            $scope.fecha.hasta = null;
            toaster.error('La fecha final no puede ser menor a la fecha inicial');
        }
    };

}]);
app.controller('MainController', ['$scope', '$rootScope', "BuscarPOIService", "toaster", function ($scope, $rootScope, BuscarPOIService, toaster) {
    'use strict';

    $scope.showLoadingBackdrop = false;

    $rootScope.$on("showLoadingBackdrop", function (event, state) {
        $scope.showLoadingBackdrop = state;
    });
}]);
app.factory('LoadingBackdrop', ['$rootScope', function ($rootScope) {
    'use strict';

    return {
        show: function () {
            $rootScope.$emit("showLoadingBackdrop", true);
        },
        hide: function () {
            $rootScope.$emit("showLoadingBackdrop", false);
        }
    };

}]);
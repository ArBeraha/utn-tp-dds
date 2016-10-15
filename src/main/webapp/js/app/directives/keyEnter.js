app.directive('keyEnter', function () {
    'use strict';

    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                event.target.blur();
                scope.$apply(function () {
                    scope.$eval(attrs.keyEnter);
                });

                event.preventDefault();
            }
        });
    };
});
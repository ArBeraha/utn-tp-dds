app.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
        templateUrl: 'login.html',
        controller: 'LoginController',
    })

    .when('/terminal', {
        templateUrl: 'busqueda.html',
        controller: 'BuscadorController',
    });

});
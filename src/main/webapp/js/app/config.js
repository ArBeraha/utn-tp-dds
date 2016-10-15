app.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
        templateUrl: 'login-form.html',
        controller: 'LoginController',
    })

    .when('/terminal', {
        templateUrl: 'busqueda.html',
        controller: 'BuscadorController',
    });

});
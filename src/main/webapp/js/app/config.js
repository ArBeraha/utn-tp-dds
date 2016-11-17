app.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
        templateUrl: 'login-form.html',
        controller: 'LoginController',
    })

    .when('/terminal', {
        templateUrl: 'busqueda.html',
        controller: 'BuscadorController',
    })

    .when('/admin', {
        templateUrl: 'menu-administrador.html',
        controller: 'AdminController',
    })

    .when('/admin/acciones', {
        templateUrl: 'configurar-acciones.html',
        controller: 'ConfigurarAccionesController',
    })

    .when('/admin/historial-busquedas', {
        templateUrl: 'historial-busquedas.html',
        controller: 'HistorialBusquedasController',
    });

});
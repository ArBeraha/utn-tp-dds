package ar.edu.utn.frba.dds.model.acciones.ante.busqueda;

import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.app.App;

public enum AccionAnteBusquedasEnum { 
	NOTIFICAR_ADMINISTRADOR(1), ALMACENAR_RESULTADOS(2);
	
	private final int id;

	private AccionAnteBusquedasEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}

package ar.edu.utn.frba.dds.model.accion;

public enum Primitivas {
	ActualizarLocalesComerciales(1), BajaPoisInactivos(2), AgregarAccionesATodos(3), DefinirProcesoMultiple(4);
	
	private final int id;

	private Primitivas(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

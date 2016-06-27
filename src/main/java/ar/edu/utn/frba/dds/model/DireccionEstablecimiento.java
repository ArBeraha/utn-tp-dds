package ar.edu.utn.frba.dds.model;

public class DireccionEstablecimiento extends Direccion {

    private String departamento;
    private String unidad;
    private String codigoPostal;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(final String departamento) {
        this.departamento = departamento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(final String unidad) {
        this.unidad = unidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(final String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

}

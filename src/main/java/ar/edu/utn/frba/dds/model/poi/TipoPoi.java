package ar.edu.utn.frba.dds.model.poi;

public enum TipoPoi {
    CGP("CGP"), LOCAL_COMERCIAL("local comercial"), PARADA_COLECTIVO("parada de colectivo"),
    SUCURSAL_BANCO("sucursal de banco");

    private final String texto;

    private TipoPoi(final String unTexto) {
        texto = unTexto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

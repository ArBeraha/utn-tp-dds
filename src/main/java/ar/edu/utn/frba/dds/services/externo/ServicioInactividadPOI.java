package ar.edu.utn.frba.dds.services.externo;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ar.edu.utn.frba.dds.model.accion.BajaInactividad;

public interface ServicioInactividadPOI {
    List<BajaInactividad> getPoisInactivos() throws JsonParseException, JsonMappingException, IOException;
}

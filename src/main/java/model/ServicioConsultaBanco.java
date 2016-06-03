package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ServicioConsultaBanco {

    public List<SucursalBanco> getBancosExternos() throws JsonParseException, JsonMappingException, IOException, UnknownHostException;
}

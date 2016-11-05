package ar.edu.utn.frba.dds.model.user;

import javax.persistence.Entity;

@Entity
public class Administrador extends TipoUsuario {
	
    public Administrador() {
        nombreTipoUsuario = "Administrador";

    }
}

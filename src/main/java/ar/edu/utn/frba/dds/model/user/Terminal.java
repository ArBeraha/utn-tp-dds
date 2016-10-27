package ar.edu.utn.frba.dds.model.user;

import ar.edu.utn.frba.dds.model.accion.*;

import java.util.ArrayList;

import javax.persistence.Entity;

import antlr.collections.List;

@Entity
public class Terminal extends TipoUsuario {
	
    public Terminal() {
        nombreTipoUsuario = "Terminal";
    }
}

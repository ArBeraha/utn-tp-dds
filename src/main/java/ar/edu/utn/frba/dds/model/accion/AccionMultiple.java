package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.user.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class AccionMultiple extends Accion {
    
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "accion_acciones")
    private List<Accion> acciones;
    
	public AccionMultiple(){}
	
    public AccionMultiple(List<Accion> acciones){
        this.acciones = acciones;
        acciones.forEach( x -> System.out.println(x.getId()));
        this.nombre = "Accion Multiple " + acciones.stream().map(x -> x.nombre).collect(Collectors.toList());
    }

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando " + nombre);
        return acciones.stream().allMatch(a -> a.executeWithReport(usuario, params));
    }

    @Override
    public boolean undo(Usuario usuario, List<Integer> params) {
        return acciones.stream().allMatch(a -> a.undo(usuario,params));
    }

	public List<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}
}

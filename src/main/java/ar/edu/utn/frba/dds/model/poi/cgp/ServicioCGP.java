package ar.edu.utn.frba.dds.model.poi.cgp;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.poi.horario.Horarios;
import ar.edu.utn.frba.dds.model.poi.horario.HorariosEspeciales;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class ServicioCGP {

	@Id @GeneratedValue
	private int id;
    private String nombre;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Horarios horarios;
    @Transient
    private HorariosEspeciales horariosEspeciales;

    public ServicioCGP(){}
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Horarios getHorarios() {
        return horarios;
    }
    
    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        if (horariosEspeciales.contiene(fechaHoraActual)) {
            return horariosEspeciales.atiende(fechaHoraActual);
        } else {
            return horarios.atiende(fechaHoraActual);
        }
    }

}

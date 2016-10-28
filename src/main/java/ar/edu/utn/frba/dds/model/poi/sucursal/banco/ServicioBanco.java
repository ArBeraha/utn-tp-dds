package ar.edu.utn.frba.dds.model.poi.sucursal.banco;

import javax.persistence.Transient;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.poi.horario.Horarios;
import ar.edu.utn.frba.dds.model.poi.horario.HorariosEspeciales;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ServicioBanco {

	@Id @GeneratedValue
	private int id;
    private String nombre;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Horarios horarios;
    @Transient
    private HorariosEspeciales horariosEspeciales;

    public ServicioBanco() {
        horariosEspeciales = new HorariosEspeciales();
    }

    public HorariosEspeciales getHorariosEspeciales() {
		return horariosEspeciales;
	}

	public void setHorariosEspeciales(HorariosEspeciales horariosEspeciales) {
		this.horariosEspeciales = horariosEspeciales;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    public Horarios getHorarios() {
        return horarios;
    }

    protected boolean estaDisponible(DateTime unaFechaHora) {
        if (horariosEspeciales.contiene(unaFechaHora)) {
            return horariosEspeciales.atiende(unaFechaHora);
        } else {
            return horarios.atiende(unaFechaHora);
        }
    }
}

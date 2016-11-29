package ar.edu.utn.frba.dds.dao;

import java.awt.Polygon;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.cgp.CGP;
import ar.edu.utn.frba.dds.model.poi.cgp.Comuna;
import ar.edu.utn.frba.dds.model.poi.cgp.ServicioCGP;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorarioEspecial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.ServicioBanco;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.SucursalBanco;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaBanco;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaBancoImpl;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaCGP;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaCGPImpl;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

@SuppressWarnings("unchecked")
public class PoiDAO implements WithGlobalEntityManager {

	public void start() {
		if (isEmpty())
			populatePois();
		else
			App.setPuntosDeInteres(getPoisPersistidos());
		agregarNuevosPoisExternos();
	}

	public List<PuntoDeInteres> getPoisPersistidos() {
		return entityManager().createQuery("FROM PuntoDeInteres").getResultList();
	}

	public boolean isEmpty() {
		return entityManager().createQuery("FROM PuntoDeInteres").getResultList().isEmpty();
	}

	public void agregarNuevosPoisExternos() {
		getNuevosPoisExternos().forEach(x -> agregarPuntoDeInteres(x));
	}

	public Set<PuntoDeInteres> getNuevosPoisExternos() {
		Set<PuntoDeInteres> nuevosPoi = new HashSet<>();
		nuevosPoi.addAll(getNuevosBancosExternos());
		nuevosPoi.addAll(getNuevosCGPExternos());
		return nuevosPoi;
	}

	private Set<PuntoDeInteres> getNuevosBancosExternos() {
		Set<PuntoDeInteres> nuevosBancos = new HashSet<>();
		List<SucursalBanco> bancosExistentes = App.getPuntosDeInteres().stream()
				.filter(x -> x.getClass() == SucursalBanco.class).map(x -> (SucursalBanco) x)
				.collect(Collectors.toList());
		ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
		try {
			boolean esNuevo = true;
			for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos("", "")) {
				for (SucursalBanco sucursalExistente : bancosExistentes) {
					if ((sucursalBancoExterna.getSucursal() == sucursalExistente.getSucursal())
							&& (sucursalBancoExterna.getNombre() == sucursalExistente.getNombre())) {
						esNuevo = false;
						break;
					}

				}
				if (esNuevo)
					nuevosBancos.add(sucursalBancoExterna);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nuevosBancos;
	}

	private Set<PuntoDeInteres> getNuevosCGPExternos() {
		Set<PuntoDeInteres> nuevosCGP = new HashSet<>();
		ServicioConsultaCGP servicioCGP = new ServicioConsultaCGPImpl();
		List<CGP> CGPExistentes = App.getPuntosDeInteres().stream().filter(x -> x.getClass() == CGP.class)
				.map(x -> (CGP) x).collect(Collectors.toList());
		try {
			boolean esNuevo = true;
			for (CGP cgpExterno : servicioCGP.getCentrosExternos("")) {
				for (CGP cgpExistente : CGPExistentes) {
					if ((cgpExistente.getComuna() == cgpExterno.getComuna())
							&& cgpExistente.getZonas().containsAll(cgpExterno.getZonas())) {
						esNuevo = false;
						break;
					}
				}
				if (esNuevo)
					nuevosCGP.add(cgpExterno);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nuevosCGP;
	}

	private void populatePois() {
		LocalComercial local;
		Rubro rubroLibreria;
		Geolocalizacion geolocalizacionLocal;
		LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
		LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
		LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
		LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
		LocalTime horaInicioSabado = new LocalTime(10, 0);
		LocalTime horaFinSabado = new LocalTime(13, 30);
		local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
		local.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(6, horaInicioSabado, horaFinSabado));

		// setUp para esCercano
		rubroLibreria = new Rubro();
		geolocalizacionLocal = new Geolocalizacion(12, 28);
		rubroLibreria.setNombre("Libreria Escolar");
		rubroLibreria.setRadioCercania(5);
		local.setGeolocalizacion(geolocalizacionLocal);
		local.setNombre("Regla y compás");
		local.setRubro(rubroLibreria);
		HashSet<String> palabrasClave = new HashSet<String>();
		palabrasClave.add("Tienda");
		local.setPalabrasClave(palabrasClave);
		local.agregarRangoHorario(
				new RangoHorarioEspecial(new LocalDate(2016, 10, 23), horaInicioLunesAViernes, horaFinLunesAViernes));

		CGP cgp;
		Comuna comuna;
		Polygon superficie;
		Geolocalizacion geolocalizacionCGP;
		cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
		comuna = new Comuna();
		superficie = new Polygon();
		superficie.addPoint(0, 0);
		superficie.addPoint(0, 10);
		superficie.addPoint(10, 10);
		superficie.addPoint(10, 0);
		comuna.setSuperficie(superficie);
		cgp.setComuna(comuna);
		geolocalizacionCGP = new Geolocalizacion(5, 5);
		cgp.setGeolocalizacion(geolocalizacionCGP);
		ServicioCGP servicioRentas = new ServicioCGP();
		servicioRentas.setNombre("Rentas");
		servicioRentas.agregarRangoHorario(new RangoHorario(5, 10, 0, 18, 0));
		servicioRentas.agregarRangoHorario(new RangoHorario(6, 10, 0, 18, 0));
		Set<ServicioCGP> servicios = new HashSet<ServicioCGP>();
		servicios.add(servicioRentas);
		cgp.setServicios(servicios);
		HashSet<String> palabras = new HashSet<String>();
		palabras.add("CGP");
		palabras.add("Chacabuco");
		cgp.setPalabrasClave(palabras);

		ParadaColectivo parada = new ParadaColectivo();
		parada.setGeolocalizacion(new Geolocalizacion(12, 58));
		parada.setLinea("103");
		Set<String> pal = new HashSet<>();
		pal.add("Colectivo");
		pal.add("Bondi");
		parada.setPalabrasClave(pal);

		SucursalBanco sucursal = new SucursalBanco(new DateTimeProviderImpl(new DateTime()));
		sucursal.setBanco("Nacion");
		ServicioBanco servicioB = new ServicioBanco();
		servicioB.setNombre("Asesoramiento");
		Geolocalizacion geolocalizacionSucursal = new Geolocalizacion(12, 28);
		sucursal.setGeolocalizacion(geolocalizacionSucursal);
		Set<ServicioBanco> servicios2 = new HashSet<ServicioBanco>();
		servicios2.add(servicioB);
		LocalTime horaInicio = new LocalTime(12, 0);
		LocalTime horaFin = new LocalTime(16, 0);
		servicioB.agregarRangoHorario(new RangoHorario(1, horaInicio, horaFin));
		servicioB.agregarRangoHorario(new RangoHorario(2, horaInicio, horaFin));
		sucursal.setServicios(servicios2);

		HashSet<String> palabras2 = new HashSet<String>();
		palabras2.add("Banco");
		sucursal.setPalabrasClave(palabras2);

		agregarPuntoDeInteres(local);
		agregarPuntoDeInteres(cgp);
		agregarPuntoDeInteres(parada);
		agregarPuntoDeInteres(sucursal);
	}

	public void agregarPuntoDeInteres(PuntoDeInteres pdi) {
		entityManager().getTransaction().begin();
		entityManager().persist(pdi);
		entityManager().getTransaction().commit();
		App.getPuntosDeInteres().add(pdi);
	}

	public void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
		entityManager().getTransaction().begin();
		entityManager().remove(pdi);
		entityManager().getTransaction().commit();
		App.getPuntosDeInteres().remove(pdi);
	}

	public void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
		pdi.setDireccion(pdiNuevo.getDireccion());
		pdi.setGeolocalizacion(pdiNuevo.getGeolocalizacion());
		pdi.setPalabrasClave(pdiNuevo.getPalabrasClave());
		entityManager().getTransaction().begin();
		entityManager().merge(pdi);
		entityManager().getTransaction().commit();
	}

}

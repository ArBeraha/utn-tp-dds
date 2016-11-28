package ar.edu.utn.frba.dds.model.busqueda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@SuppressWarnings("unchecked")
public class Reporte implements WithGlobalEntityManager {

	public  Map<String, Long> busquedasPorTerminal() {
		Map<String, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando Reporte de Busquedas por terminal:");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda").getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getUsuario().getUsername(), Collectors.counting()));
			reporte.forEach(
					(terminal, cantidad) -> System.out.println("Terminal : " + terminal + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
	}

	public  Map<String, Long> busquedasPorFecha() {
		Map<String, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando de Busquedas Reporte:");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda").getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
			reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
		
	}

	public Map<String, Long> busquedasDeTerminal(int idTerminal) {
		Map<String, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando Reporte de Busquedas de Terminal " + idTerminal + ": ");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda WHERE usuario_id=" + idTerminal)
					.getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
			reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;

	}

}

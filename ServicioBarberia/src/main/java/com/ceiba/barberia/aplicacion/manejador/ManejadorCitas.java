package com.ceiba.barberia.aplicacion.manejador;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

@Component
public class ManejadorCitas {

	private final ServicioCita servicioCita;
	private final FabricaCita fabricaCita;
	
	public ManejadorCitas(ServicioCita servicioCita, FabricaCita fabricaCita) {
		this.servicioCita = servicioCita;
		this.fabricaCita = fabricaCita;
	}
	
	@Transactional
	public Cita agendarCita(Date fecha, Barbero barbero, Boolean corteCabello, 
			Boolean corteBarba, Boolean lavado, String nombreCliente) {
		Cita cita =  fabricaCita.cita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente);
		return servicioCita.agendarCita(cita);
	}
	
	public List<Cita> listarCitas() {
		return servicioCita.listarCitas();
	}
}

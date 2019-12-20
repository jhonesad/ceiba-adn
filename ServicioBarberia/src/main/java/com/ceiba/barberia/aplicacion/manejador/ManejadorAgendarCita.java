package com.ceiba.barberia.aplicacion.manejador;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

@Component
public class ManejadorAgendarCita {

	private final ServicioCita servicioCita;
	private final FabricaCita fabricaCita;
	
	public ManejadorAgendarCita(ServicioCita servicioCita, FabricaCita fabricaCita) {
		this.servicioCita = servicioCita;
		this.fabricaCita = fabricaCita;
	}
	
	@Transactional
	public ComandoCita ejecutar(ComandoCita comandoCita) {
		Cita cita =  fabricaCita.crear(comandoCita);
		cita = servicioCita.agendar(cita);
		comandoCita.setId(cita.getId());
		return comandoCita;
	}
}

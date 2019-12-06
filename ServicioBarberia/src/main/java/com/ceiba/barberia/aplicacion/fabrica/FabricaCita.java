package com.ceiba.barberia.aplicacion.fabrica;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

@Component
public class FabricaCita {
	
	public Cita cita(Date fecha, Barbero barbero, Boolean corteCabello, 
			Boolean corteBarba, Boolean lavado, String nombreCliente) {
		return new Cita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente);
	}
}

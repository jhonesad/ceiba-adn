package com.ceiba.barberia.aplicacion.fabrica;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

@Component
public class FabricaCita {
	
	public Cita crear(ComandoCita comandoCita) {
		Barbero barbero = FabricaBarbero.getInstance().crear(comandoCita.getBarbero());
		
		return new Cita(comandoCita.getId(), 
				comandoCita.getFecha(), 
				barbero, 
				comandoCita.getCorteCabello(), 
				comandoCita.getCorteBarba(), 
				comandoCita.getLavado(), 
				comandoCita.getNombreCliente());
	}
	
	public ComandoCita cita(Cita cita) {
		ComandoBarbero comandoBarbero = FabricaBarbero.getInstance().barbero(cita.getBarbero());
		
		return new ComandoCita(cita.getId(), 
				cita.getFecha(), 
				comandoBarbero, 
				cita.getCorteCabello(), 
				cita.getCorteBarba(), 
				cita.getLavado(), 
				cita.getNombreCliente());
	}
}

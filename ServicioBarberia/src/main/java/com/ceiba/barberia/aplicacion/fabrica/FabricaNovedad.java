package com.ceiba.barberia.aplicacion.fabrica;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;

@Component
public class FabricaNovedad {
	
	public Novedad crear(ComandoNovedad comandoNovedad) {
		Barbero barbero = null;
		if(comandoNovedad.getBarbero() != null) {
			barbero = FabricaBarbero.getInstance().crear(comandoNovedad.getBarbero());
		}
		
		return new Novedad(comandoNovedad.getId(), 
				comandoNovedad.getFechaInicio(), 
				comandoNovedad.getFechaFin(), 
				barbero, 
				comandoNovedad.getDescripcion(), 
				comandoNovedad.getFestivo());
	}
	
	public ComandoNovedad novedad(Novedad novedad) {
		ComandoBarbero comandoBarbero = null;
		if(novedad.getBarbero() != null) {
			comandoBarbero = FabricaBarbero.getInstance().barbero(novedad.getBarbero());
		}
		
		return new ComandoNovedad(novedad.getId(), 
				novedad.getFechaInicio(), 
				novedad.getFechaFin(),
				comandoBarbero,
				novedad.getDescripcion(),
				novedad.getFestivo());
	}
}

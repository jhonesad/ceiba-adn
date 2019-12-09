package com.ceiba.barberia.aplicacion.fabrica;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;

@Component
public class FabricaBarbero {
	
	public Barbero crear(ComandoBarbero comandoBarbero) {
		return new Barbero(comandoBarbero.getId(), comandoBarbero.getNombre());
	}
	
	public ComandoBarbero barbero(Barbero barbero) {
		return new ComandoBarbero(barbero.getId(), barbero.getNombre());
	}
	
	public static FabricaBarbero getInstance() {
		return new FabricaBarbero();
	}
}

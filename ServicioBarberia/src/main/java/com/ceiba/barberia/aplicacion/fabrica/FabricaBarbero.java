package com.ceiba.barberia.aplicacion.fabrica;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.dominio.entidades.Barbero;

@Component
public class FabricaBarbero {
	
	public Barbero barbero(String codigo, String nombre) {
		return new Barbero(codigo, nombre);
	}
}

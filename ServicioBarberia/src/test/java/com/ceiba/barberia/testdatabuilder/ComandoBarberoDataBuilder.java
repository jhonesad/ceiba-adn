package com.ceiba.barberia.testdatabuilder;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;

public class ComandoBarberoDataBuilder {

	private Long id;
	private String nombre;
	
	public ComandoBarberoDataBuilder() {
		this.id = 1l;
		this.nombre = "Barbero test";
	}
	
	public ComandoBarberoDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public ComandoBarberoDataBuilder withNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public ComandoBarbero build() {
		return new ComandoBarbero(id, nombre);
	}
	
	public static ComandoBarberoDataBuilder aComandoBarberoDataBuilder() {
		return new ComandoBarberoDataBuilder();
	}
}

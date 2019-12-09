package com.ceiba.barberia.testdatabuilder;

import com.ceiba.barberia.dominio.entidades.Barbero;

public class BarberoDataBuilder {
	
	private Long id;
	private String nombre;
	
	public BarberoDataBuilder() {
		this.id = 1l;
		this.nombre = "Barbero test";
	}
	
	public BarberoDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public BarberoDataBuilder withNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public Barbero build() {
		return new Barbero(id, nombre);
	}
	
	public static BarberoDataBuilder aBarberoDataBuilder() {
		return new BarberoDataBuilder();
	}
}

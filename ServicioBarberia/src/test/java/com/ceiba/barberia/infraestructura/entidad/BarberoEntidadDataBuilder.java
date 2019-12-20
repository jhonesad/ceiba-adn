package com.ceiba.barberia.infraestructura.entidad;

import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;

public class BarberoEntidadDataBuilder {
	
	private Long id;
	private String nombre;
	
	public BarberoEntidadDataBuilder() {
	}
	
	public BarberoEntidadDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public BarberoEntidadDataBuilder withNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public BarberoEntidad build() {
		BarberoEntidad barbero = new BarberoEntidad();
		barbero.setId(id);
		barbero.setNombre(nombre);
		return barbero;
	}
	
	public static BarberoEntidadDataBuilder aBuilder() {
		return new BarberoEntidadDataBuilder();
	}
}

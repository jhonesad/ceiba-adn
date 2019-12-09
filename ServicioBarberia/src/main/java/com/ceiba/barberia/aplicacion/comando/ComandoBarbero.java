package com.ceiba.barberia.aplicacion.comando;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(scope = ComandoBarbero.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComandoBarbero {
	
	private Long id;
	
	@NotNull
	private String nombre;
	
	public ComandoBarbero() {
		super();
	}
	
	public ComandoBarbero(Long id, String nombre) {
		this.setId(id);
		this.setNombre(nombre);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

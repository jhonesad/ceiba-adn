package com.ceiba.barberia.dominio.entidades;

public class Barbero {

	private Long id;
	private String nombre;
	
	public Barbero() {
	}
	
	public Barbero(Long id, String nombre) {
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

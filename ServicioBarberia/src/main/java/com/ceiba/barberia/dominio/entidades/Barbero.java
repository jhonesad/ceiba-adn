package com.ceiba.barberia.dominio.entidades;

public class Barbero {

	private String codigo;
	private String nombre;
	
	public Barbero() {
	}
	
	public Barbero(String codigo, String nombre) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}

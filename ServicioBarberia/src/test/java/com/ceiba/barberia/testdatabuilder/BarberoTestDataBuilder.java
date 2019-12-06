package com.ceiba.barberia.testdatabuilder;

import com.ceiba.barberia.dominio.entidades.Barbero;

public class BarberoTestDataBuilder {
	
	private String codigo;
	private String nombre;
	
	public BarberoTestDataBuilder() {
		this.codigo = "001";
		this.nombre = "Barbero test";
	}
	
	public BarberoTestDataBuilder withCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	
	public BarberoTestDataBuilder withNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public Barbero build() {
		return new Barbero(codigo, nombre);
	}
	
	public static BarberoTestDataBuilder aBarberoTestDataBuilder() {
		return new BarberoTestDataBuilder();
	}
}

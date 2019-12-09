package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;

public class NovedadDataBuilder {

	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private Barbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public NovedadDataBuilder() {
		this.id = 1l;
		this.fechaInicio = new Date();
		this.fechaFin = new Date();
		this.barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		this.descripcion = "Descripcion novedad test";
		this.festivo = false;
	}
	
	public NovedadDataBuilder withId(Long id) {
		this.id = id;
		return this;
	} 
	
	public NovedadDataBuilder withFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	public NovedadDataBuilder withFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}
	
	public NovedadDataBuilder withBarbero(Barbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public NovedadDataBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public NovedadDataBuilder withFestivo(Boolean festivo) {
		this.festivo = festivo;
		return this;
	}
	
	public Novedad build() {
		return new Novedad(id, fechaInicio, fechaFin, barbero, descripcion, festivo);
	}
	
	public static NovedadDataBuilder aNovedadDataBuilder() {
		return new NovedadDataBuilder();
	}
}

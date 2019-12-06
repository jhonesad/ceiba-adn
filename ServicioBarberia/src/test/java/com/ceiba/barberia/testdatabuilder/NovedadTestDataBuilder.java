package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;

public class NovedadTestDataBuilder {

	private Date fechaInicio;
	private Date fechaFin;
	private Barbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public NovedadTestDataBuilder() {
		this.fechaInicio = new Date();
		this.fechaFin = new Date();
		this.barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		this.descripcion = "Descripcion novedad test";
		this.festivo = false;
	}
	
	public NovedadTestDataBuilder withFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	public NovedadTestDataBuilder withFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}
	
	public NovedadTestDataBuilder withBarbero(Barbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public NovedadTestDataBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public NovedadTestDataBuilder withFestivo(Boolean festivo) {
		this.festivo = festivo;
		return this;
	}
	
	public Novedad build() {
		return new Novedad(fechaInicio, fechaFin, barbero, descripcion, festivo);
	}
	
	public static NovedadTestDataBuilder aNovedadTestDataBuilder() {
		return new NovedadTestDataBuilder();
	}
}

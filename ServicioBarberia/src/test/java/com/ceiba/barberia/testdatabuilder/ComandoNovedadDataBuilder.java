package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;

public class ComandoNovedadDataBuilder {
	
	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private ComandoBarbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public ComandoNovedadDataBuilder() {
		this.id = 1l;
		this.fechaInicio = new Date();
		this.fechaFin = new Date();
		this.barbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		this.descripcion = "Descripcion novedad test";
		this.festivo = false;
	}
	
	public ComandoNovedadDataBuilder withId(Long id) {
		this.id = id;
		return this;
	} 
	
	public ComandoNovedadDataBuilder withFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	public ComandoNovedadDataBuilder withFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}
	
	public ComandoNovedadDataBuilder withBarbero(ComandoBarbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public ComandoNovedadDataBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public ComandoNovedadDataBuilder withFestivo(Boolean festivo) {
		this.festivo = festivo;
		return this;
	}
	
	public ComandoNovedad build() {
		return new ComandoNovedad(id, fechaInicio, fechaFin, barbero, descripcion, festivo);
	}
	
	public static ComandoNovedadDataBuilder aComandoNovedadDataBuilder() {
		return new ComandoNovedadDataBuilder();
	}
}

package com.ceiba.barberia.dominio.entidades;

import java.util.Date;

public class Novedad {
	
	private Date fechaInicio;
	private Date fechaFin;
	private Barbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public Novedad(Date fechaInicio, Date fechaFin, Barbero barbero, String descripcion, Boolean festivo) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.barbero = barbero;
		this.descripcion = descripcion;
		this.festivo = festivo;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public Barbero getBarbero() {
		return barbero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Boolean getFestivo() {
		return festivo;
	}	
}

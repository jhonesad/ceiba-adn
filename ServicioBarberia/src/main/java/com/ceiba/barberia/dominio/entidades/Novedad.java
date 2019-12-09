package com.ceiba.barberia.dominio.entidades;

import java.util.Date;

public class Novedad {
	
	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private Barbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public Novedad() {
	}
	
	public Novedad(Long id, Date fechaInicio, Date fechaFin, Barbero barbero, String descripcion, Boolean festivo) {
		this.setId(id);
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
		this.setBarbero(barbero);
		this.setDescripcion(descripcion);
		this.setFestivo(festivo);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Barbero getBarbero() {
		return barbero;
	}

	public void setBarbero(Barbero barbero) {
		this.barbero = barbero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getFestivo() {
		return festivo;
	}

	public void setFestivo(Boolean festivo) {
		this.festivo = festivo;
	}	
}

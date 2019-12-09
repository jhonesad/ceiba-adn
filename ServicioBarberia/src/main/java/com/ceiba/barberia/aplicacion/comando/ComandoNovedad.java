package com.ceiba.barberia.aplicacion.comando;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(scope = ComandoNovedad.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComandoNovedad {

	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private ComandoBarbero barbero;
	private String descripcion;
	private Boolean festivo;
	
	public ComandoNovedad() {
		super();
	}
	
	public ComandoNovedad(Long id, Date fechaInicio, Date fechaFin, 
			ComandoBarbero barbero, String descripcion, Boolean festivo) {
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

	public ComandoBarbero getBarbero() {
		return barbero;
	}

	public void setBarbero(ComandoBarbero barbero) {
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

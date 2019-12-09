package com.ceiba.barberia.dominio.entidades;

import java.util.Date;

public class Cita {
	
	private Long id;
	private Date fecha;
	private Barbero barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public Cita() {
	}
	
	public Cita(Long id, Date fecha, Barbero barbero, Boolean corteCabello, 
			Boolean corteBarba, Boolean lavado, String nombreCliente) {
		this.setId(id);
		this.setFecha(fecha);
		this.setBarbero(barbero);
		this.setCorteCabello(corteCabello);
		this.setCorteBarba(corteBarba);
		this.setLavado(lavado);
		this.setNombreCliente(nombreCliente);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return new Date(fecha.getTime());
	}

	public void setFecha(Date fecha) {
		this.fecha = new Date(fecha.getTime());
	}

	public Barbero getBarbero() {
		return barbero;
	}

	public void setBarbero(Barbero barbero) {
		this.barbero = barbero;
	}

	public Boolean getCorteCabello() {
		return corteCabello;
	}

	public void setCorteCabello(Boolean corteCabello) {
		this.corteCabello = corteCabello;
	}

	public Boolean getCorteBarba() {
		return corteBarba;
	}

	public void setCorteBarba(Boolean corteBarba) {
		this.corteBarba = corteBarba;
	}

	public Boolean getLavado() {
		return lavado;
	}

	public void setLavado(Boolean lavado) {
		this.lavado = lavado;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
}

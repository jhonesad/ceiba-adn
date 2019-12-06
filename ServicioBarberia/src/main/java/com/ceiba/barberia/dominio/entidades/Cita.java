package com.ceiba.barberia.dominio.entidades;

import java.util.Date;

public class Cita {
	
	private Date fecha;
	private Barbero barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public Cita() {
	}
	
	public Cita(Date fecha, Barbero barbero, Boolean corteCabello, Boolean corteBarba, 
			Boolean lavado, String nombreCliente) {
		this.setFecha(fecha);
		this.setBarbero(barbero);
		this.setCorteCabello(corteCabello);
		this.setCorteBarba(corteBarba);
		this.setLavado(lavado);
		this.setNombreCliente(nombreCliente);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

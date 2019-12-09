package com.ceiba.barberia.aplicacion.comando;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(scope = ComandoCita.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComandoCita {
	
	private Long id;
	
	@NotNull
	private Date fecha;
	
	@NotNull
	private ComandoBarbero barbero;
	
	@NotNull
	private Boolean corteCabello;
	
	@NotNull
	private Boolean corteBarba;
	
	@NotNull
	private Boolean lavado;
	
	@NotNull
	private String nombreCliente;
	
	public ComandoCita() {
		super();
	}
	
	public ComandoCita(Long id, Date fecha, ComandoBarbero barbero, Boolean corteCabello,
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

	public ComandoBarbero getBarbero() {
		return barbero;
	}

	public void setBarbero(ComandoBarbero barbero) {
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

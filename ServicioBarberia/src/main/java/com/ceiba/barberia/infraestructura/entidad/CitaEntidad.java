package com.ceiba.barberia.infraestructura.entidad;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CITA")
public class CitaEntidad {
	
	@EmbeddedId
	private CitaEntidadPK pk;
	
	@Column(name = "CORTE_CABELLO", nullable = false)
	private boolean corteCabello = true;
	
	@Column(name = "CORTE_BARBA", nullable = false)
	private boolean corteBarba = false;
	
	@Column(name = "LAVADO", nullable = false)
	private boolean lavado = false;
	
	@Column(name = "NOMBRE_CLIENTE")
	private String nombreCliente;
	
	public CitaEntidad() {
	}

	public CitaEntidadPK getPk() {
		return pk;
	}

	public void setPk(CitaEntidadPK pk) {
		this.pk = pk;
	}

	public boolean isCorteCabello() {
		return corteCabello;
	}

	public void setCorteCabello(boolean corteCabello) {
		this.corteCabello = corteCabello;
	}

	public boolean isCorteBarba() {
		return corteBarba;
	}

	public void setCorteBarba(boolean corteBarba) {
		this.corteBarba = corteBarba;
	}

	public boolean isLavado() {
		return lavado;
	}

	public void setLavado(boolean lavado) {
		this.lavado = lavado;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
}

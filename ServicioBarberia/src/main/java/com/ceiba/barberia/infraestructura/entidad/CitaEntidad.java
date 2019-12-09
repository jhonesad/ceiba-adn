package com.ceiba.barberia.infraestructura.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CITA")
public class CitaEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_cita")
	@SequenceGenerator(name="secuencia_cita", sequenceName = "CITA_SECUENCIA", initialValue=1, allocationSize=100)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "FECHA", nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name = "ID_BARBERO", nullable = false)
	private BarberoEntidad barbero;
	
	@Column(name = "CORTE_CABELLO", nullable = false)
	private boolean corteCabello = true;
	
	@Column(name = "CORTE_BARBA", nullable = false)
	private boolean corteBarba = false;
	
	@Column(name = "LAVADO", nullable = false)
	private boolean lavado = false;
	
	@Column(name = "NOMBRE_CLIENTE", nullable = false)
	private String nombreCliente;
	
	public CitaEntidad() {
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

	public BarberoEntidad getBarbero() {
		return barbero;
	}

	public void setBarbero(BarberoEntidad barbero) {
		this.barbero = barbero;
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

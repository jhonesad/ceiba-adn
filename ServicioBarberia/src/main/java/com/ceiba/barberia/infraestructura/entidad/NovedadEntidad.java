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

@Entity
@Table(name = "NOVEDAD")
public class NovedadEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_novedad")
	@SequenceGenerator(name="secuencia_novedad", sequenceName = "NOVEDAD_SECUENCIA", initialValue=1, allocationSize=100)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "FECHA_INICIO", nullable = false)
	private Date fechaInicio;
	
	@Column(name = "FECHA_FIN", nullable = false)
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name = "ID_BARBERO")
	private BarberoEntidad barbero;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "FESTIVO", nullable = false)
	private Boolean festivo = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return new Date(fechaInicio.getTime());
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = new Date(fechaInicio.getTime());
	}

	public Date getFechaFin() {
		return new Date(fechaFin.getTime());
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = new Date(fechaFin.getTime());
	}

	public BarberoEntidad getBarbero() {
		return barbero;
	}

	public void setBarbero(BarberoEntidad barbero) {
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

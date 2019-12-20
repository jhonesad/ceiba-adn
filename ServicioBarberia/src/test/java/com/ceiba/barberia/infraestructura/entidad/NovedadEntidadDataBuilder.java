package com.ceiba.barberia.infraestructura.entidad;

import java.util.Date;

import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.NovedadEntidad;

public class NovedadEntidadDataBuilder {

	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private BarberoEntidad barbero;
	private String descripcion;
	private Boolean festivo;
	
	public NovedadEntidadDataBuilder() {
	}
	
	public NovedadEntidadDataBuilder withId(Long id) {
		this.id = id;
		return this;
	} 
	
	public NovedadEntidadDataBuilder withFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	public NovedadEntidadDataBuilder withFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}
	
	public NovedadEntidadDataBuilder withBarbero(BarberoEntidad barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public NovedadEntidadDataBuilder withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public NovedadEntidadDataBuilder withFestivo(Boolean festivo) {
		this.festivo = festivo;
		return this;
	}
	
	public NovedadEntidad build() {
		NovedadEntidad novedad = new NovedadEntidad();
		novedad.setId(id);
		novedad.setFechaInicio(fechaInicio);
		novedad.setFechaFin(fechaFin);
		novedad.setBarbero(barbero);
		novedad.setDescripcion(descripcion);
		novedad.setFestivo(festivo);
		
		return novedad;
	}
	
	public static NovedadEntidadDataBuilder aBuilder() {
		return new NovedadEntidadDataBuilder();
	}
}

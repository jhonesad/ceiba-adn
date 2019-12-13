package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidad;

public class CitaEntidadDataBuilder {

	private Long id;
	private Date fecha;
	private BarberoEntidad barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public CitaEntidadDataBuilder() {
	}
	
	public CitaEntidadDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public CitaEntidadDataBuilder withFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}
	
	public CitaEntidadDataBuilder withBarbero(BarberoEntidad barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public CitaEntidadDataBuilder withCorteCabello(Boolean corteCabello) {
		this.corteCabello = corteCabello;
		return this;
	}
	
	public CitaEntidadDataBuilder withCorteBarba(Boolean corteBarba) {
		this.corteBarba = corteBarba;
		return this;
	}
	
	public CitaEntidadDataBuilder withLavado(Boolean lavado) {
		this.lavado = lavado;
		return this;
	}
	
	public CitaEntidadDataBuilder withNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
		return this;
	}
	
	public CitaEntidad build() {
		CitaEntidad cita = new CitaEntidad();
		cita.setId(id);
		cita.setFecha(fecha);
		cita.setBarbero(barbero);
		cita.setCorteCabello(corteCabello);
		cita.setCorteBarba(corteBarba);
		cita.setLavado(lavado);
		cita.setNombreCliente(nombreCliente);
		
		return cita;
	}
	
	public static CitaEntidadDataBuilder aCitaDataBuilder() {
		return new CitaEntidadDataBuilder();
	}
}

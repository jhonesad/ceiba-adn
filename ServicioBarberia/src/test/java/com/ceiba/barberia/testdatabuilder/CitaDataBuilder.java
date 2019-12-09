package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

public class CitaDataBuilder {
	
	private Long id;
	private Date fecha;
	private Barbero barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public CitaDataBuilder() {
		this.id = 1l;
		this.fecha = new Date();
		this.barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		this.corteCabello = true;
		this.corteBarba = false;
		this.lavado = true;
		this.nombreCliente = "Cliente test";
	}
	
	public CitaDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public CitaDataBuilder withFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}
	
	public CitaDataBuilder withBarbero(Barbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public CitaDataBuilder withCorteCabello(Boolean corteCabello) {
		this.corteCabello = corteCabello;
		return this;
	}
	
	public CitaDataBuilder withCorteBarba(Boolean corteBarba) {
		this.corteBarba = corteBarba;
		return this;
	}
	
	public CitaDataBuilder withLavado(Boolean lavado) {
		this.lavado = lavado;
		return this;
	}
	
	public CitaDataBuilder withNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
		return this;
	}
	
	public Cita build() {
		return new Cita(id, fecha, barbero, corteCabello, corteBarba, 
				lavado, nombreCliente);
	}
	
	public static CitaDataBuilder aCitaDataBuilder() {
		return new CitaDataBuilder();
	}
}

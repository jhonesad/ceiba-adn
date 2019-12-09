package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;

public class ComandoCitaDataBuilder {
	
	private Long id;
	private Date fecha;
	private ComandoBarbero barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public ComandoCitaDataBuilder() {
		this.id = 1l;
		this.fecha = new Date();
		this.barbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		this.corteCabello = true;
		this.corteBarba = false;
		this.lavado = true;
		this.nombreCliente = "Cliente test";
	}
	
	public ComandoCitaDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public ComandoCitaDataBuilder withFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}
	
	public ComandoCitaDataBuilder withBarbero(ComandoBarbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public ComandoCitaDataBuilder withCorteCabello(Boolean corteCabello) {
		this.corteCabello = corteCabello;
		return this;
	}
	
	public ComandoCitaDataBuilder withCorteBarba(Boolean corteBarba) {
		this.corteBarba = corteBarba;
		return this;
	}
	
	public ComandoCitaDataBuilder withLavado(Boolean lavado) {
		this.lavado = lavado;
		return this;
	}
	
	public ComandoCitaDataBuilder withNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
		return this;
	}
	
	public ComandoCita build() {
		return new ComandoCita(id, fecha, barbero, corteCabello, corteBarba, 
				lavado, nombreCliente);
	}
	
	public static ComandoCitaDataBuilder aComandoCitaDataBuilder() {
		return new ComandoCitaDataBuilder();
	}
}

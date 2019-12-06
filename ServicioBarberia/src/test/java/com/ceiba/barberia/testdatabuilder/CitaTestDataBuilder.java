package com.ceiba.barberia.testdatabuilder;

import java.util.Date;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

public class CitaTestDataBuilder {
	
	private Date fecha;
	private Barbero barbero;
	private Boolean corteCabello;
	private Boolean corteBarba;
	private Boolean lavado;
	private String nombreCliente;
	
	public CitaTestDataBuilder() {
		this.fecha = new Date();
		this.barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		this.corteCabello = true;
		this.corteBarba = false;
		this.lavado = true;
		this.nombreCliente = "Cliente test";
	}
	
	public CitaTestDataBuilder withFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}
	
	public CitaTestDataBuilder withBarbero(Barbero barbero) {
		this.barbero = barbero;
		return this;
	}
	
	public CitaTestDataBuilder withCorteCabello(Boolean corteCabello) {
		this.corteCabello = corteCabello;
		return this;
	}
	
	public CitaTestDataBuilder withCorteBarba(Boolean corteBarba) {
		this.corteBarba = corteBarba;
		return this;
	}
	
	public CitaTestDataBuilder withLavado(Boolean lavado) {
		this.lavado = lavado;
		return this;
	}
	
	public CitaTestDataBuilder withNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
		return this;
	}
	
	public Cita build() {
		return new Cita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente);
	}
	
	public static CitaTestDataBuilder aCitaTestDataBuilder() {
		return new CitaTestDataBuilder();
	}
}

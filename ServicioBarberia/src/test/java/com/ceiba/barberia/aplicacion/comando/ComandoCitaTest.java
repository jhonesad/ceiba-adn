package com.ceiba.barberia.aplicacion.comando;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;

public class ComandoCitaTest {

	@Test
	public void validarInstancia() {
		ComandoCita cita = new ComandoCita();
		
		assertNotNull(cita);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		Date fecha = new Date();
		ComandoBarbero barbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		Boolean corteCabello = false;
		Boolean corteBarba = true;
		Boolean lavado = false;
		String nombreCliente = "cliente de prueba";
		
		ComandoCita cita = new ComandoCita(null, new Date(), null, true, false, true, "");
		cita.setId(id);
		cita.setFecha(fecha);
		cita.setBarbero(barbero);
		cita.setCorteCabello(corteCabello);
		cita.setCorteBarba(corteBarba);
		cita.setLavado(lavado);
		cita.setNombreCliente(nombreCliente);
		
		assertEquals(id, cita.getId());
		assertEquals(fecha, cita.getFecha());
		assertNotNull(cita.getBarbero());
		assertEquals(barbero.getId(), cita.getBarbero().getId());
		assertEquals(barbero.getNombre(), cita.getBarbero().getNombre());
		assertEquals(corteCabello, cita.getCorteCabello());
		assertEquals(corteBarba, cita.getCorteBarba());
		assertEquals(lavado, cita.getLavado());
		assertEquals(nombreCliente, cita.getNombreCliente());
	}
}

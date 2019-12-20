package com.ceiba.barberia.dominio.entidades;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class CitaTest {

	@Test
	public void validarInstancia() {
		Cita cita = new Cita();
		
		assertNotNull(cita);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		Date fecha = new Date();
		Barbero barbero = Barbero.builder().id(1l).nombre("test").build(); 
		Boolean corteCabello = false;
		Boolean corteBarba = true;
		Boolean lavado = false;
		String nombreCliente = "cliente de prueba";
		
		Cita cita = new Cita(null, new Date(), null, true, false, true, "");
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

package com.ceiba.barberia.dominio.entidades;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class CitaTest {

	@Test
	public void validarInstancia() {
		Cita cita = new Cita();
		
		assertNotNull(cita);
	}
	
	@Test
	public void validarGettersSetters() {
		Date fecha = new Date();
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		Boolean corteCabello = false;
		Boolean corteBarba = true;
		Boolean lavado = false;
		String nombreCliente = "cliente de prueba";
		
		Cita cita = new Cita(new Date(), null, true, false, true, "");
		cita.setFecha(fecha);
		cita.setBarbero(barbero);
		cita.setCorteCabello(corteCabello);
		cita.setCorteBarba(corteBarba);
		cita.setLavado(lavado);
		cita.setNombreCliente(nombreCliente);
		
		assertEquals(fecha, cita.getFecha());
		assertNotNull(cita.getBarbero());
		assertEquals(barbero.getCodigo(), cita.getBarbero().getCodigo());
		assertEquals(barbero.getNombre(), cita.getBarbero().getNombre());
		assertEquals(corteCabello, cita.getCorteCabello());
		assertEquals(corteBarba, cita.getCorteBarba());
		assertEquals(lavado, cita.getLavado());
		assertEquals(nombreCliente, cita.getNombreCliente());
	}
}

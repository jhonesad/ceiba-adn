package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class CitaEntidadTest {

	@Test
	public void validateGettersSetters() {
		Long id = 1l;
		Date fecha = new Date();
		BarberoEntidad barbero = new BarberoEntidad();
		boolean corteCabello = true;
		boolean corteBarba = false;
		boolean lavado = false;
		String nombreCliente = "cliente";
		
		CitaEntidad citaEntidad = new CitaEntidad();
		citaEntidad.setId(id);
		citaEntidad.setFecha(fecha);
		citaEntidad.setBarbero(barbero);
		citaEntidad.setCorteCabello(corteCabello);
		citaEntidad.setCorteBarba(corteBarba);
		citaEntidad.setLavado(lavado);
		citaEntidad.setNombreCliente(nombreCliente);
		
		assertNotNull(citaEntidad);
		assertEquals(id, citaEntidad.getId());
		assertEquals(fecha, citaEntidad.getFecha());
		assertEquals(barbero, citaEntidad.getBarbero());
		assertEquals(corteCabello, citaEntidad.isCorteCabello());
		assertEquals(corteBarba, citaEntidad.isCorteBarba());
		assertEquals(lavado, citaEntidad.isLavado());
		assertEquals(nombreCliente, citaEntidad.getNombreCliente());
	}
}

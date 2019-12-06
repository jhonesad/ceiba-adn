package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.Assert.*;

import org.junit.Test;

public class CitaEntidadTest {

	@Test
	public void validateGettersSetters() {
		CitaEntidadPK pk = new CitaEntidadPK();
		boolean corteCabello = true;
		boolean corteBarba = false;
		boolean lavado = false;
		String nombreCliente = "cliente";
		
		CitaEntidad citaEntidad = new CitaEntidad();
		citaEntidad.setPk(pk);
		citaEntidad.setCorteCabello(corteCabello);
		citaEntidad.setCorteBarba(corteBarba);
		citaEntidad.setLavado(lavado);
		citaEntidad.setNombreCliente(nombreCliente);
		
		assertNotNull(citaEntidad);
		assertNotNull(citaEntidad.getPk());
		assertEquals(corteCabello, citaEntidad.isCorteCabello());
		assertEquals(corteBarba, citaEntidad.isCorteBarba());
		assertEquals(lavado, citaEntidad.isLavado());
		assertEquals(nombreCliente, citaEntidad.getNombreCliente());
	}
}

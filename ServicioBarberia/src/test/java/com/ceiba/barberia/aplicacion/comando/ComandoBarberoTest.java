package com.ceiba.barberia.aplicacion.comando;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComandoBarberoTest {

	@Test
	public void validarInstancia() {
		ComandoBarbero barbero = new ComandoBarbero();
		
		assertNotNull(barbero);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 2l;
		String nombre = "Test barbero";
		ComandoBarbero barbero = new ComandoBarbero(1l, null);
		
		barbero.setId(id);
		barbero.setNombre(nombre);
		
		boolean validacion = barbero.getId().equals(id);
		validacion = validacion && barbero.getNombre().equals(nombre);
		
		assertTrue(validacion);
	}
}

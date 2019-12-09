package com.ceiba.barberia.dominio.entidades;

import static org.junit.Assert.*;

import org.junit.Test;

public class BarberoTest {
	
	@Test
	public void validarInstancia() {
		Barbero barbero = new Barbero();
		
		assertNotNull(barbero);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 2l;
		String nombre = "Test barbero";
		Barbero barbero = new Barbero(1l, null);
		
		barbero.setId(id);
		barbero.setNombre(nombre);
		
		boolean validacion = barbero.getId().equals(id);
		validacion = validacion && barbero.getNombre().equals(nombre);
		
		assertTrue(validacion);
	}

}

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
		String codigo = "002";
		String nombre = "Test barbero";
		Barbero barbero = new Barbero("001", null);
		
		barbero.setCodigo(codigo);
		barbero.setNombre(nombre);
		
		boolean validacion = barbero.getCodigo().equals(codigo);
		validacion = validacion && barbero.getNombre().equals(nombre);
		
		assertTrue(validacion);
	}

}

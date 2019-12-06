package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class BarberoEntidadTest {

	@Test
	public void validarGettersSetters() {
		String codigo = "111";
		String nombre = "nombre";
		
		BarberoEntidad barberoEntidad = new BarberoEntidad();
		barberoEntidad.setCodigo(codigo);
		barberoEntidad.setNombre(nombre);
		
		assertEquals(codigo, barberoEntidad.getCodigo());
		assertEquals(nombre, barberoEntidad.getNombre());
	}
}

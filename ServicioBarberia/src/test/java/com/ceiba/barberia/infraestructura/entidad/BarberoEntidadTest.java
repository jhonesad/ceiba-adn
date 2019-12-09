package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class BarberoEntidadTest {

	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		String nombre = "nombre";
		
		BarberoEntidad barberoEntidad = new BarberoEntidad();
		barberoEntidad.setId(id);
		barberoEntidad.setNombre(nombre);
		
		assertEquals(id, barberoEntidad.getId());
		assertEquals(nombre, barberoEntidad.getNombre());
	}
}

package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Barbero;

public class FabricaBarberoTest {

	@Test
	public void validarInstanciaFabrica() {
		String codigo = "999";
		String nombre = "nombre";
		FabricaBarbero fabrica = new FabricaBarbero();
		
		Barbero instancia = fabrica.barbero(codigo, nombre);
		
		assertNotNull(instancia);
		assertEquals(codigo, instancia.getCodigo());
		assertEquals(nombre, instancia.getNombre());
	}
}
